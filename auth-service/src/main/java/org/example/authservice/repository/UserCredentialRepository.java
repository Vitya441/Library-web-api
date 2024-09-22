package org.example.authservice.repository;

import org.example.authservice.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {


    Optional<UserCredential> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
