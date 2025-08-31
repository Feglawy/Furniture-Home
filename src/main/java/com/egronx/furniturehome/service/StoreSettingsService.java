package com.egronx.furniturehome.service;

import com.egronx.furniturehome.dto.StoreSettingsDTO;
import com.egronx.furniturehome.entity.StoreSettings;
import com.egronx.furniturehome.repository.StoreSettingsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreSettingsService {

    @Autowired
    private StoreSettingsRepository storeSettingsRepository;

    public StoreSettingsDTO getStoreSettings() {
        StoreSettings settings = storeSettingsRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Store Settings not found"));
        return StoreSettingsDTO.builder()
                .name(settings.getName())
                .logoUrl(settings.getLogoUrl())
                .aboutImageUrl(settings.getAboutImageUrl())
                .aboutDescription(settings.getAboutDescription())
                .termsAndConditions(settings.getTermsAndConditions())
                .facebookUrl(settings.getFacebookUrl())
                .whatsappNumber(settings.getWhatsappNumber())
                .phoneNumber(settings.getPhoneNumber())
                .secondPhoneNumber(settings.getSecondPhoneNumber())
                .build();
    }

    @Transactional
    public StoreSettingsDTO createOrUpdateStoreSettings(StoreSettingsDTO dto) {
        // Try to fetch the single row (id = 1)
        StoreSettings storeSettings = storeSettingsRepository.findById(1L)
                .orElse(new StoreSettings()); // create new if not exists

        // Map DTO fields to entity
        storeSettings.setName(dto.getName());
        storeSettings.setLogoUrl(dto.getLogoUrl());
        storeSettings.setAboutImageUrl(dto.getAboutImageUrl());
        storeSettings.setAboutDescription(dto.getAboutDescription());
        storeSettings.setTermsAndConditions(dto.getTermsAndConditions());
        storeSettings.setFacebookUrl(dto.getFacebookUrl());
        storeSettings.setWhatsappNumber(dto.getWhatsappNumber());
        storeSettings.setPhoneNumber(dto.getPhoneNumber());
        storeSettings.setSecondPhoneNumber(dto.getSecondPhoneNumber());

        // Save or update
        storeSettingsRepository.save(storeSettings);

        return dto;
    }

    private StoreSettings mapDtoToEntity(StoreSettingsDTO dto) {
        StoreSettings entity = new StoreSettings();
        updateEntityFromDto(entity, dto);
        return entity;
    }

    private void updateEntityFromDto(StoreSettings entity, StoreSettingsDTO dto) {
        entity.setName(dto.getName());
        entity.setLogoUrl(dto.getLogoUrl());
        entity.setAboutImageUrl(dto.getAboutImageUrl());
        entity.setAboutDescription(dto.getAboutDescription());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setSecondPhoneNumber(dto.getSecondPhoneNumber());
        entity.setWhatsappNumber(dto.getWhatsappNumber());
        entity.setTermsAndConditions(dto.getTermsAndConditions());
        entity.setFacebookUrl(dto.getFacebookUrl());
    }
}
