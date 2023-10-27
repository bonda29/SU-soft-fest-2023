package tech.bonda.sufest2023.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bonda.sufest2023.models.DTOs.RegisterDto;
import tech.bonda.sufest2023.models.DTOs.UsernamePasswordDto;
import tech.bonda.sufest2023.services.AuthenticationService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto data) {
        return authenticationService.register(data);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UsernamePasswordDto data) {
        return authenticationService.login(data);
    }
}
