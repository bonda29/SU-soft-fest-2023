package tech.bonda.sufest2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.bonda.sufest2023.models.User;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.id = :id")
    void updatePassword(@Param("id") long id, @Param("newPassword") String newPassword);
}