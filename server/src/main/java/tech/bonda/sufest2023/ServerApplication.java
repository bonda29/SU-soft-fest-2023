package tech.bonda.sufest2023;

import com.stripe.Stripe;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.bonda.sufest2023.models.Role;
import tech.bonda.sufest2023.repository.RoleRepo;
import tech.bonda.sufest2023.repository.UserRepo;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
        Stripe.apiKey = "sk_test_51O5nGIJBrDD3W9P8k1Oqx58JvYMqX3kCLDUGFnf7xV5lwtUmpXVsFlXUfiZeCOphHLxFavnNMqAmQbdVXZu1x9Ux00mdkyiyak";

    }

    @Bean
    CommandLineRunner run(RoleRepo roleRepo, UserRepo userRepo, PasswordEncoder passwordEncode) {
        return args -> {
            if (roleRepo.findByAuthority("COMPANY").isPresent()) return;
            Role adminRole = roleRepo.save(new Role("COMPANY"));
            roleRepo.save(new Role("USER"));
        };
    }
}
