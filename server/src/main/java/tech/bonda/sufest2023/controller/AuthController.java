package tech.bonda.sufest2023.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bonda.sufest2023.models.DTOs.UserDto;
import tech.bonda.sufest2023.models.DTOs.UsernamePasswordDto;
import tech.bonda.sufest2023.services.AuthenticationService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto data) {
        return authenticationService.register(data);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UsernamePasswordDto data) {
        return authenticationService.login(data);
    }
}
