package com.example.hvbackend.userManagement;
import com.example.hvbackend.userManagement.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    boolean existsByEmail(@NotNull String email);

    boolean existsByUsername(@NotNull String username);
}
