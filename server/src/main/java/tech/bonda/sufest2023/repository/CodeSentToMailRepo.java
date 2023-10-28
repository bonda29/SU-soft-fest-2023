package tech.bonda.sufest2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.bonda.sufest2023.models.CodeSentToMail;

import java.util.Optional;

public interface CodeSentToMailRepo extends JpaRepository<CodeSentToMail, Integer> {
    Optional<CodeSentToMail> findByEmail(String email);

    void deleteByEmail(String email);

    @Modifying
    @Query("UPDATE CodeSentToMail c SET c.code = :newCode WHERE c.email = :email")
    void updateCode(@Param("newCode") String newCode, @Param("email") String email);

}