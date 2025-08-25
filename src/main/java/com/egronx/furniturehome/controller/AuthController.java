package com.egronx.furniturehome.controller;

import com.egronx.furniturehome.dto.LoginRequest;
import com.egronx.furniturehome.dto.LoginResponse;
import com.egronx.furniturehome.dto.MessageResponse;
import com.egronx.furniturehome.dto.SignupRequest;
import com.egronx.furniturehome.dto.UserResponse;
import com.egronx.furniturehome.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {
        try {
            UserResponse user = authenticationService.signup(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("already exists")) {
                return ResponseEntity.badRequest().body(MessageResponse.builder()
                        .message("User with this email already exists")
                        .build());
            }
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = authenticationService.login(request);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(MessageResponse.builder()
                    .message("Invalid email or password")
                    .build());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageResponse.builder()
                    .message("User not found")
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout() {
        // Note: JWT tokens are stateless, so logout is handled client-side
        // by removing the token from storage
        return ResponseEntity.ok(MessageResponse.builder()
                .message("Logged out successfully")
                .build());
    }
} 