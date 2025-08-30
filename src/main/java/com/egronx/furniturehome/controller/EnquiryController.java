package com.egronx.furniturehome.controller;

import com.egronx.furniturehome.dto.Request.EnquiryRequestDTO;
import com.egronx.furniturehome.dto.Request.ReplayDTO;
import com.egronx.furniturehome.dto.Response.EnquiryResponseDTO;
import com.egronx.furniturehome.entity.Enquiry;
import com.egronx.furniturehome.entity.User;
import com.egronx.furniturehome.service.EnquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enquiries")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class EnquiryController {

    private final EnquiryService enquiryService;

    @PostMapping
    public ResponseEntity<Enquiry> createEnquiry(
            @RequestParam Long userId,
            @Valid @RequestBody EnquiryRequestDTO request) {

        Enquiry enquiry = enquiryService.createEnquiry(request.getContent(), userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(enquiry);
    }

    @GetMapping
    public ResponseEntity<?> getAllEnquiries() {
        return ResponseEntity.status(HttpStatus.OK).body(
                enquiryService.getAllEnquiries()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEnquiryById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(enquiryService.getEnquiryById(id));
    }

    @PutMapping("/{id}/reply")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> replyToEnquiry(@PathVariable Long id,
                                            @Valid @RequestBody ReplayDTO reply) {
        return ResponseEntity.status(HttpStatus.OK).body(enquiryService.replyToEnquiry(id, reply.getReply()));
    }

    @PutMapping("/{id}/close")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> closeEnquiry(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(enquiryService.closeEnquiry(id));
    }
}
