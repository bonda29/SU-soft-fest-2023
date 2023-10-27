package tech.bonda.sufest2023.services;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nulabinc.zxcvbn.Zxcvbn;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bonda.sufest2023.models.Company;
import tech.bonda.sufest2023.models.DTOs.RegisterDto;
import tech.bonda.sufest2023.models.DTOs.UsernamePasswordDto;
import tech.bonda.sufest2023.models.Role;
import tech.bonda.sufest2023.models.User;
import tech.bonda.sufest2023.repository.CompanyRepo;
import tech.bonda.sufest2023.repository.RoleRepo;
import tech.bonda.sufest2023.repository.UserRepo;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    private final RoleRepo roleRepo;

    private final UserRepo userRepo;

    private final CompanyRepo companyRepo;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final Zxcvbn zxcvbn = new Zxcvbn();


    public AuthenticationService(RoleRepo roleRepo, UserRepo userRepo, CompanyRepo companyRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.companyRepo = companyRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    private boolean isPasswordStrong(String password) {
        return zxcvbn.measure(password).getScore() >= 2;
    }

    public ResponseEntity<?> register(RegisterDto data) {
        if (loadObjectByUsername(data.getUsername()) != null)
        {
            return ResponseEntity.status(400).body("Username is already taken");
        }
/*        if (!isPasswordStrong(data.getPassword()))
        {
            return ResponseEntity.status(400).body("Password is not strong enough");
        }*/

        return switch (data.getRole())
        {
            case "USER" -> registerUser(data);
            case "COMPANY" -> registerCompany(data);
            default -> ResponseEntity.status(400).body("Invalid role");
        };
    }

    private ResponseEntity<?> registerUser(RegisterDto data) {
        String encodedPassword = passwordEncoder.encode(data.getPassword());
        Role role = roleRepo.findByAuthority("USER").orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> authorities = new HashSet<>();
        authorities.add(role);

        User user = User.builder()
                .username(data.getUsername())
                .name(data.getName())
                .password(encodedPassword)
                .email(data.getEmail())
                .date_of_registration(LocalDate.now().toString())
                .authorities(authorities)
                .build();
        return ResponseEntity.ok(userRepo.save(user));
    }

    private ResponseEntity<?> registerCompany(RegisterDto data) {
        String encodedPassword = passwordEncoder.encode(data.getPassword());
        Role role = roleRepo.findByAuthority("COMPANY").orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> authorities = new HashSet<>();
        authorities.add(role);

        Company company = Company.builder()
                .username(data.getUsername())
                .name(data.getName())
                .password(encodedPassword)
                .email(data.getEmail())
                .date_of_registration(LocalDate.now().toString())
                .authorities(authorities)
                .build();
        return ResponseEntity.ok(companyRepo.save(company));
    }

    public ResponseEntity<?> login(UsernamePasswordDto data) {
        String username = data.getUsername();
        String password = data.getPassword();

        Authentication auth;
        try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        Optional<?> objectOptional = (Optional<?>) loadObjectByUsername(username);
        if (objectOptional.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        Object object = objectOptional.get();
        String token = tokenService.generateJwt(auth);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonResponse = mapper.createObjectNode();

        if (object instanceof User user) {
            jsonResponse.put("id", user.getId());
            jsonResponse.put("email", user.getEmail());
            jsonResponse.put("username", user.getUsername());
            jsonResponse.put("role", "USER");
        } else if (object instanceof Company company) {
            jsonResponse.put("id", company.getId());
            jsonResponse.put("email", company.getEmail());
            jsonResponse.put("username", company.getUsername());
            jsonResponse.put("role", "COMPANY");
        }

        jsonResponse.put("token", token);

        return ResponseEntity.ok(jsonResponse);
    }


    private Object loadObjectByUsername(String username) {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent())
        {
            return user;
        }
        Optional<Company> company = companyRepo.findByUsername(username);
        if (company.isPresent())
        {
            return company;
        }
        return null;
    }

}
