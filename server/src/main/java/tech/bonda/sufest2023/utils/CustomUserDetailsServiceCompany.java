package tech.bonda.sufest2023.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.bonda.sufest2023.repository.CompanyRepo;

@Service
public class CustomUserDetailsServiceCompany implements UserDetailsService {
    @Autowired
    private CompanyRepo companyRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return companyRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Company is not valid"));
    }
}