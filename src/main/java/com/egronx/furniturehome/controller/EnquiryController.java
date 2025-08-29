package com.egronx.furniturehome.controller;

import com.egronx.furniturehome.entity.Enquiry;
import com.egronx.furniturehome.entity.User;
import com.egronx.furniturehome.service.EnquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enquiries")
@RequiredArgsConstructor
public class EnquiryController {

    private final EnquiryService enquiryService;

    @PostMapping
    public ResponseEntity<Enquiry> createEnquiry(@RequestParam String content,
                                                 @RequestParam Long userId) {
        User user = new User();
        user.setId(userId);

        Enquiry enquiry = enquiryService.createEnquiry(content, user);
        return ResponseEntity.ok(enquiry);
    }

    @GetMapping
    public ResponseEntity<List<Enquiry>> getAllEnquiries() {
        return ResponseEntity.ok(enquiryService.getAllEnquiries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enquiry> getEnquiryById(@PathVariable Long id) {
        return enquiryService.getEnquiryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/reply")
    public ResponseEntity<Enquiry> replyToEnquiry(@PathVariable Long id,
                                                  @RequestParam String reply) {
        return ResponseEntity.ok(enquiryService.replyToEnquiry(id, reply));
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<Enquiry> closeEnquiry(@PathVariable Long id) {
        return ResponseEntity.ok(enquiryService.closeEnquiry(id));
    }
}
