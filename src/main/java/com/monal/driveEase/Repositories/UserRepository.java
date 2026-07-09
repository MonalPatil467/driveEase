package com.monal.driveEase.Repositories;

import com.monal.driveEase.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    //Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
