package com.egronx.furniturehome.service;

import com.egronx.furniturehome.dto.Response.EnquiryResponseDTO;
import com.egronx.furniturehome.entity.Enquiry;
import com.egronx.furniturehome.entity.User;
import com.egronx.furniturehome.repository.EnquiryRepository;
import com.egronx.furniturehome.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnquiryService {

    private final EnquiryRepository enquiryRepository;
    private final UserRepository userRepository;

    public EnquiryService(EnquiryRepository enquiryRepository, UserRepository userRepository) {
        this.enquiryRepository = enquiryRepository;
        this.userRepository = userRepository;
    }

    public List<EnquiryResponseDTO> getAllEnquiries() {

        List<Enquiry> enquiries = enquiryRepository.findAll();
        List<EnquiryResponseDTO> enquiriesDTO = new ArrayList<>();
        for (Enquiry enquiry : enquiries) {
            enquiriesDTO.add(mapToDTO(enquiry));
        }
        return enquiriesDTO;
    }

    public EnquiryResponseDTO getEnquiryById(Long id) {
        Enquiry enquiry = enquiryRepository.findById(id).orElseThrow(()-> new RuntimeException("Not found"));
        return mapToDTO(enquiry);
    }

    public Enquiry createEnquiry(Enquiry enquiry) {
        return enquiryRepository.save(enquiry);
    }

    public Enquiry createEnquiry(String content, Long userId) {
        Enquiry enquiry = new Enquiry();
        enquiry.setContent(content);
        enquiry.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!")));
        enquiry.setClosed(false);
        return enquiryRepository.save(enquiry);
    }

    public Enquiry updateEnquiry(Long id, Enquiry enquiryDetails) {
        return enquiryRepository.findById(id).map(enquiry -> {
            enquiry.setContent(enquiryDetails.getContent());
            enquiry.setAdminReply(enquiryDetails.getAdminReply());
            enquiry.setClosed(enquiryDetails.isClosed());
            enquiry.setUser(enquiryDetails.getUser());
            return enquiryRepository.save(enquiry);
        }).orElseThrow(() -> new RuntimeException("Enquiry not found with id " + id));
    }

    public void deleteEnquiry(Long id) {
        enquiryRepository.deleteById(id);
    }

    // للرد على استعلام
    public EnquiryResponseDTO replyToEnquiry(Long id, String reply) {
        Enquiry enquiry = enquiryRepository.findById(id).orElseThrow(() -> new RuntimeException("Enquiry not found with id " + id));
        enquiry.setAdminReply(reply);
        enquiryRepository.save(enquiry);
        return mapToDTO(enquiry);
    }

    // لإغلاق استعلام
    public EnquiryResponseDTO closeEnquiry(Long id) {
        Enquiry enquiry = enquiryRepository.findById(id).orElseThrow(() -> new RuntimeException("Enquiry not found with id " + id));
        enquiry.setClosed(true);
        enquiryRepository.save(enquiry);
        return mapToDTO(enquiry);
    }

    private EnquiryResponseDTO mapToDTO(Enquiry enquiry) {
        return EnquiryResponseDTO.builder()
                .id(enquiry.getId())
                .userId(enquiry.getUser().getId())
                .content(enquiry.getContent())
                .adminReply(enquiry.getAdminReply())
                .closed(enquiry.isClosed())
                .build();
    }
}
