package com.egronx.furniturehome.service;

import com.egronx.furniturehome.dto.StoreSettingsDTO;
import com.egronx.furniturehome.entity.StoreSettings;
import com.egronx.furniturehome.repository.StoreSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreSettingsService {

    @Autowired
    private StoreSettingsRepository storeSettingsRepository;

    public StoreSettingsDTO getStoreSettings() {
        StoreSettings settings = storeSettingsRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Store Settings not found"));
        return new StoreSettingsDTO(settings);
    }

    public StoreSettingsDTO createStoreSettings(StoreSettingsDTO dto) {
        if (storeSettingsRepository.existsById(1L)) {
            throw new RuntimeException("Store Settings already exists");
        }

        StoreSettings storeSettings = mapDtoToEntity(dto);
        storeSettings.setId(1L);

        return new StoreSettingsDTO(storeSettingsRepository.save(storeSettings));
    }

    public StoreSettingsDTO updateStoreSettings(StoreSettingsDTO dto) {
        StoreSettings storeSettings = storeSettingsRepository.findById(1L)
                .orElseGet(StoreSettings::new);

        storeSettings.setId(1L);
        updateEntityFromDto(storeSettings, dto);

        return new StoreSettingsDTO(storeSettingsRepository.save(storeSettings));
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
