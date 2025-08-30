package com.egronx.furniturehome.service;

import com.egronx.furniturehome.entity.Enquiry;
import com.egronx.furniturehome.entity.User;
import com.egronx.furniturehome.repository.EnquiryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnquiryService {

    private final EnquiryRepository enquiryRepository;

    public EnquiryService(EnquiryRepository enquiryRepository) {
        this.enquiryRepository = enquiryRepository;
    }

    public List<Enquiry> getAllEnquiries() {
        return enquiryRepository.findAll();
    }

    public Optional<Enquiry> getEnquiryById(Long id) {
        return enquiryRepository.findById(id);
    }

    // الميثود القديمة
    public Enquiry createEnquiry(Enquiry enquiry) {
        return enquiryRepository.save(enquiry);
    }

    // الميثود الجديدة (عشان الكود في الـ Controller يشتغل)
    public Enquiry createEnquiry(String content, User user) {
        Enquiry enquiry = new Enquiry();
        enquiry.setContent(content);
        enquiry.setUser(user);
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
    public Enquiry replyToEnquiry(Long id, String reply) {
        return enquiryRepository.findById(id).map(enquiry -> {
            enquiry.setAdminReply(reply);
            return enquiryRepository.save(enquiry);
        }).orElseThrow(() -> new RuntimeException("Enquiry not found with id " + id));
    }

    // لإغلاق استعلام
    public Enquiry closeEnquiry(Long id) {
        return enquiryRepository.findById(id).map(enquiry -> {
            enquiry.setClosed(true);
            return enquiryRepository.save(enquiry);
        }).orElseThrow(() -> new RuntimeException("Enquiry not found with id " + id));
    }
}
