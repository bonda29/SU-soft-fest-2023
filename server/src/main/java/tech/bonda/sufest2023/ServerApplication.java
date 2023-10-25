package tech.bonda.sufest2023;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.bonda.sufest2023.models.Role;
import tech.bonda.sufest2023.models.User;
import tech.bonda.sufest2023.repository.UserRepo;
import tech.bonda.sufest2023.repository.RoleRepo;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepo roleRepo, UserRepo userRepo, PasswordEncoder passwordEncode) {
		return args -> {
			if (roleRepo.findByAuthority("ADMIN").isPresent()) return;
			Role adminRole = roleRepo.save(new Role("ADMIN"));
			roleRepo.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			User admin = User.builder()
					.username("admin")
					.password(passwordEncode.encode("admin"))
					.authorities(roles)
					.build();
			userRepo.save(admin);
		};
	}
}
