package tech.bonda.sufest2023.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nulabinc.zxcvbn.Zxcvbn;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bonda.sufest2023.models.DTOs.UsernamePasswordDto;
import tech.bonda.sufest2023.models.DTOs.UserDto;
import tech.bonda.sufest2023.models.Role;
import tech.bonda.sufest2023.models.User;
import tech.bonda.sufest2023.repository.UserRepo;
import tech.bonda.sufest2023.repository.RoleRepo;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    private final RoleRepo roleRepo;

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final Zxcvbn zxcvbn = new Zxcvbn();


    public AuthenticationService(RoleRepo roleRepo, UserRepo userRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    private boolean isPasswordStrong(String password) {
        return zxcvbn.measure(password).getScore() >= 2;
    }

    public ResponseEntity<?> register(UserDto data) {

        if (!isPasswordStrong(data.getPassword()))
        {
            return ResponseEntity.status(400).body("Password is not strong enough");
        }
        String encodedPassword = passwordEncoder.encode(data.getPassword());
        Role role = roleRepo.findByAuthority("USER").orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> authorities = new HashSet<>();
        authorities.add(role);

        User user = User.builder()
                .username(data.getUsername())
                .password(encodedPassword)
                .email(data.getEmail())
                .phone(data.getPhone())
                .date_of_registration(LocalDate.now().toString())
                .authorities(authorities)
                .build();
        return ResponseEntity.ok(userRepo.save(user));
    }


    public ResponseEntity<?> login(UsernamePasswordDto data) {

        String username = data.getUsername();
        String password = data.getPassword();

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        String token = tokenService.generateJwt(auth);

        Optional<?> user = loadUserByUsername(username);
        if (user.isEmpty())
        {
            return ResponseEntity.status(401).body("Invalid username/password supplied");
        }
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonResponse = mapper.createObjectNode();
        jsonResponse.set("user", mapper.valueToTree(user.get()));
        jsonResponse.set("token", mapper.valueToTree(token));

        return ResponseEntity.ok(jsonResponse);

    }

    private Optional<?> loadUserByUsername(String username) throws UsernameNotFoundException {
        //noinspection UnnecessaryLocalVariable
        Optional<?> user = userRepo.findByUsername(username);
        return user;
    }

}
