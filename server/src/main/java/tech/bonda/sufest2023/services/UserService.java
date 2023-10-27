package tech.bonda.sufest2023.services;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.bonda.sufest2023.repository.UserRepo;

@Service
public class UserService implements UserDetailsService {

    @SuppressWarnings({"FieldCanBeLocal"})
    private final PasswordEncoder encoder;

    private final UserRepo userRepo;

    public UserService(PasswordEncoder encoder, UserRepo userRepo) {
        this.encoder = encoder;
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User is not valid"));
    }

}