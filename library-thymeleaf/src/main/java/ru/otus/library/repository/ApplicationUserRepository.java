package ru.otus.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.domain.ApplicationUser;

import java.util.Optional;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, String> {
    Optional<ApplicationUser> findByUsername(String username);
}
