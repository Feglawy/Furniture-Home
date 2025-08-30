package com.egronx.furniturehome.controller;

import com.egronx.furniturehome.dto.UpdateUserRequest;
import com.egronx.furniturehome.dto.UserResponse;
import com.egronx.furniturehome.dto.MessageResponse;
import com.egronx.furniturehome.security.MyUserDetails;
import com.egronx.furniturehome.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long id,
                                            @AuthenticationPrincipal MyUserDetails userDetails) {
        try {
            // Check if the authenticated user is requesting their own profile
            if (!Objects.equals(id, userDetails.getId())) {
                return ResponseEntity.badRequest().body(MessageResponse.builder().message("Unauthorized").build());
            }

            UserResponse user = userService.getUserProfile(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserProfile(
            @PathVariable Long id,
            @AuthenticationPrincipal MyUserDetails userDetails,
            @Valid @RequestBody UpdateUserRequest request) {
        try {
            // Check if the authenticated user is updating their own profile
            if (!Objects.equals(id, userDetails.getId())) {
                return ResponseEntity.badRequest().body(MessageResponse.builder().message("Unauthorized").build());
            }
            UserResponse updatedUser = userService.updateUser(id, request);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }
} 