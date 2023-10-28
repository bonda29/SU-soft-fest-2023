package tech.bonda.sufest2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.bonda.sufest2023.models.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepo extends JpaRepository<Company, Integer> {
    Optional<Company> findByUsername(String username);

    Optional<List<Company>> findAllByNameContaining(String keyword);

    Optional<Company> findByEmail(String email);

    @Modifying
    @Query("UPDATE Company c SET c.password = :newPassword WHERE c.id = :id")
    void updatePassword(@Param("id") long id, @Param("newPassword") String newPassword);}
