package tech.bonda.sufest2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.bonda.sufest2023.models.Company;

import java.util.Optional;

public interface CompanyRepo extends JpaRepository<Company, Integer> {
    Optional<Company> findByUsername(String username);
}
