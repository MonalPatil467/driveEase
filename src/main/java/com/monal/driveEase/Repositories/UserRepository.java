package com.monal.driveEase.Repositories;

import com.monal.driveEase.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
