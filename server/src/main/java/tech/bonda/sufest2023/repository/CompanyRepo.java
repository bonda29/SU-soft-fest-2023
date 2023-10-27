package tech.bonda.sufest2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.bonda.sufest2023.models.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepo extends JpaRepository<Company, Integer> {
    Optional<Company> findByUsername(String username);

    Optional<List<Company>> findAllByNameLike(String keyword);
}
