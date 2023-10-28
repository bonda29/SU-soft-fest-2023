package tech.bonda.sufest2023.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bonda.sufest2023.models.DTOs.EmailDTO;
import tech.bonda.sufest2023.models.DTOs.ForgotPasswordDTO;
import tech.bonda.sufest2023.models.DTOs.RegisterDto;
import tech.bonda.sufest2023.models.DTOs.UsernamePasswordDto;
import tech.bonda.sufest2023.services.AuthenticationService;
import tech.bonda.sufest2023.services.ForgotPasswordService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto data) {
        return authenticationService.register(data);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsernamePasswordDto data) {
        return authenticationService.login(data);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordDTO data) {
        return forgotPasswordService.forgotPassword(data);
    }

    @PostMapping("/sendNewPassword")
    public ResponseEntity<?> sendNewPassword(@RequestBody ObjectNode data) {
        String email = data.get("email").asText();
        EmailDTO emailDTO = new EmailDTO(email, "", "New password", "");
        return forgotPasswordService.sendEmailWithTemplate(emailDTO);
    }
}
