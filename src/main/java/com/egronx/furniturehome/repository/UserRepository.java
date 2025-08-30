package com.egronx.furniturehome.repository;

import com.egronx.furniturehome.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find user by email
     * @param email user's email
     * @return Optional containing user if found
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Check if user exists by email
     * @param email user's email
     * @return true if user exists, false otherwise
     */
    boolean existsByEmail(String email);
    
    /**
     * Find user by reset token
     * @param resetToken reset token
     * @return Optional containing user if found
     */
    Optional<User> findByResetToken(String resetToken);
}