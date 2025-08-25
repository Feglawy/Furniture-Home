package com.egronx.furniturehome.service;

import com.egronx.furniturehome.dto.UpdateUserRequest;
import com.egronx.furniturehome.dto.UserResponse;
import com.egronx.furniturehome.entity.User;
import com.egronx.furniturehome.entity.UserRole;
import com.egronx.furniturehome.repository.UserRepository;
import com.egronx.furniturehome.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().getName())
                .build();
    }
    
    public User createUser(User user) {
        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Set default role if not provided
        if (user.getRole() == null) {
            UserRole customerRole = userRoleRepository.findByName("CUSTOMER")
                    .orElseGet(() -> userRoleRepository.save(UserRole.builder().name("CUSTOMER").build()));
            user.setRole(customerRole);
        }
        
        return userRepository.save(user);
    }


    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    public UserResponse updateUser(Long userId, UpdateUserRequest updateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Update fields if provided
        if (updateRequest.getName() != null) {
            user.setName(updateRequest.getName());
        }
        if (updateRequest.getPhone() != null) {
            user.setPhone(updateRequest.getPhone());
        }
        if (updateRequest.getLocation() != null) {
            user.setLocation(updateRequest.getLocation());
        }
        
        User updatedUser = userRepository.save(user);
        
        return UserResponse.builder()
                .id(updatedUser.getId())
                .name(updatedUser.getName())
                .email(updatedUser.getEmail())
                .phone(updatedUser.getPhone())
                .location(updatedUser.getLocation())
                .build();
    }

    public UserResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .location(user.getLocation())
                .build();
    }


    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .location(user.getLocation())
                .build();
    }
} 