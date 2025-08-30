package com.egronx.furniturehome.service;

import com.egronx.furniturehome.dto.Request.LoginRequest;
import com.egronx.furniturehome.dto.Response.LoginResponse;
import com.egronx.furniturehome.dto.Request.PasswordResetConfirmRequest;
import com.egronx.furniturehome.dto.Request.PasswordResetRequest;
import com.egronx.furniturehome.dto.Request.SignupRequest;
import com.egronx.furniturehome.dto.Response.UserResponse;
import com.egronx.furniturehome.entity.User;
import com.egronx.furniturehome.repository.UserRepository;
import com.egronx.furniturehome.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    /**
     * Register a new user
     * @param request signup request
     * @return user response
     */
    public UserResponse signup(SignupRequest request) {

        // Check if user already exists
        if (userService.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User with this email already exists");
        }

        // Create new user
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(request.getPassword())
                .build();

        User savedUser = userService.createUser(user);
        
        return userService.toUserResponse(savedUser);
    }

    /**
     * Authenticate user and generate JWT token
     * @param request login request
     * @return login response with token and user data
     */
    public LoginResponse login(LoginRequest request) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Get user details
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        
        // Generate JWT token
        String token = jwtService.generateToken(userDetails);
        
        // Get user from database
        User user = userService.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return LoginResponse.builder()
                .token(token)
                .user(userService.toUserResponse(user))
                .build();
    }

    /**
     * Request password reset
     * @param request password reset request
     */
    public void requestPasswordReset(PasswordResetRequest request) {
        User user = userService.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with this email"));

        // Generate reset token
        String resetToken = UUID.randomUUID().toString();
        LocalDateTime expiryTime = LocalDateTime.now().plusHours(1); // Token expires in 1 hour

        // Save reset token to user
        user.setResetToken(resetToken);
        user.setResetTokenExpiry(expiryTime);
        userRepository.save(user);

        // Send reset email
        emailService.sendPasswordResetEmail(user.getEmail(), resetToken);
    }

    /**
     * Confirm password reset
     * @param request password reset confirmation request
     */
    public void confirmPasswordReset(PasswordResetConfirmRequest request) {
        User user = userRepository.findByResetToken(request.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid or expired reset token"));

        // Check if token is expired
        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Reset token has expired");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }

} 