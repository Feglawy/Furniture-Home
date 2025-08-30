package com.egronx.furniturehome.dto;

import com.egronx.furniturehome.entity.StoreSettings;
import lombok.Data;

@Data
public class StoreSettingsDTO {


    public StoreSettingsDTO(StoreSettings storeSettings) {
        this.name = storeSettings.getName();
        this.logoUrl = storeSettings.getLogoUrl();
        this.aboutImageUrl = storeSettings.getAboutImageUrl();
        this.aboutDescription = storeSettings.getAboutDescription();
        this.termsAndConditions = storeSettings.getTermsAndConditions();
        this.facebookUrl = storeSettings.getFacebookUrl();
        this.whatsappNumber = storeSettings.getWhatsappNumber();
        this.phoneNumber = storeSettings.getPhoneNumber();
        this.secondPhoneNumber = storeSettings.getSecondPhoneNumber();
    }

    private String name;
    private String logoUrl;
    private String aboutImageUrl;
    private String aboutDescription;
    private String termsAndConditions;
    private String facebookUrl;
    private String whatsappNumber;
    private String phoneNumber;
    private String secondPhoneNumber;
}
