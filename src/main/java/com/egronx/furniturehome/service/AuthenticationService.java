package com.egronx.furniturehome.service;

import com.egronx.furniturehome.dto.LoginRequest;
import com.egronx.furniturehome.dto.LoginResponse;
import com.egronx.furniturehome.dto.SignupRequest;
import com.egronx.furniturehome.dto.UserResponse;
import com.egronx.furniturehome.entity.User;
import com.egronx.furniturehome.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

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
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
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


} 