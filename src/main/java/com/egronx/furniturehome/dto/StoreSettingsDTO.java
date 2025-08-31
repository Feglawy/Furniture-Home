package com.egronx.furniturehome.dto;

import com.egronx.furniturehome.entity.StoreSettings;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreSettingsDTO {
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
