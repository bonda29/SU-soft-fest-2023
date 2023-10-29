package tech.bonda.sufest2023.services;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tech.bonda.sufest2023.models.CodeSentToMail;
import tech.bonda.sufest2023.models.Company;
import tech.bonda.sufest2023.models.DTOs.EmailDTO;
import tech.bonda.sufest2023.models.DTOs.ForgotPasswordDTO;
import tech.bonda.sufest2023.models.User;
import tech.bonda.sufest2023.repository.CodeSentToMailRepo;
import tech.bonda.sufest2023.repository.CompanyRepo;
import tech.bonda.sufest2023.repository.UserRepo;

import java.util.Optional;
import java.util.Random;

@Service
@Transactional
@PropertySource("classpath:application.properties")
public class ForgotPasswordService {

    private final JavaMailSender javaMailSender;
    private final CodeSentToMailRepo codeSentToMailRepository;
    private final PasswordEncoder passwordEncoder;
    private final TemplateEngine templateEngine;
    private final UserRepo userRepo;
    private final CompanyRepo companyRepo;


    @Value("${generated.code.length}")
    private int CODE_LENGTH;

    public ForgotPasswordService(JavaMailSender javaMailSender, CodeSentToMailRepo codeSentToMailRepository, PasswordEncoder passwordEncoder, TemplateEngine templateEngine, UserRepo userRepo, CompanyRepo companyRepo) {
        this.javaMailSender = javaMailSender;
        this.codeSentToMailRepository = codeSentToMailRepository;
        this.passwordEncoder = passwordEncoder;
        this.templateEngine = templateEngine;
        this.userRepo = userRepo;
        this.companyRepo = companyRepo;
    }

    private String generateCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++)
        {
            int digit = random.nextInt(10);
            code.append(digit);
        }
        return code.toString();
    }

    public ResponseEntity<String> sendEmailWithTemplate(EmailDTO emailDTO) {
        Object object = loadObjectByEmail(emailDTO.getTo());
        if (object == null)
        {
            return ResponseEntity.badRequest().body("Email is not present!");
        }

        CodeSentToMail codeSentToMail = new CodeSentToMail();

        codeSentToMail.setEmail(emailDTO.getTo());
        String generatedPassword = generateCode();
        codeSentToMail.setCode(generatedPassword);


        if (codeSentToMailRepository.findByEmail(emailDTO.getTo()).isPresent())
        {
            codeSentToMailRepository.updateCode(generatedPassword, emailDTO.getTo());
            System.out.println("Code updated successfully");
        }
        else
        {
            codeSentToMailRepository.save(codeSentToMail);
        }
        try
        {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("admin@bonda.tech");
            helper.setTo(emailDTO.getTo());
            helper.setSubject(emailDTO.getSubject());

            String emailContent = createEmailContent(emailDTO.getName(), generatedPassword);
            helper.setText(emailContent, true); // Set the second parameter to true for HTML content

            javaMailSender.send(message);
            return ResponseEntity.ok("Email sent successfully");
        } catch (MessagingException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
        }
    }

    public ResponseEntity<String> forgotPassword(ForgotPasswordDTO data) {



        String email = data.getEmail();
        String passwordSendToEmail = data.getPasswordSendToEmail();
        String newPassword = data.getNewPassword();

        String passwordFromDB = codeSentToMailRepository.findByEmail(email).get().getCode();
        Object object = loadObjectByEmail(email);
        if (object == null)
        {
            return ResponseEntity.badRequest().body("Email is incorrect!");
        }
        if (passwordFromDB.equals(passwordSendToEmail))
        {
            if (!data.getNewPassword().equals(data.getRepeatPassword()))
            {
                return ResponseEntity.badRequest().body("Passwords don't match!");
            }
            if (object instanceof Company)
            {
                Company company = companyRepo.findByEmail(email).get();
                company.setPassword(passwordEncoder.encode(newPassword));
                companyRepo.updatePassword(company.getId(), company.getPassword());
            }
            else
            {
                User user = userRepo.findByEmail(email).get();
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepo.updatePassword(user.getId(), user.getPassword());
            }
            return ResponseEntity.ok().body("Password changed successfully!");
        }
        else
        {
            return ResponseEntity.badRequest().body("The code is incorrect!");
        }
    }

    private String createEmailContent(String name, String content) {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("content", content);
        return templateEngine.process("index", context);
    }
    private Object loadObjectByEmail(String email) {
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            return user.get(); // Assuming a User is found
        }

        Optional<Company> company = companyRepo.findByEmail(email);
        if (company.isPresent()) {
            return company.get(); // Assuming a Company is found
        }

        return null;
    }
}