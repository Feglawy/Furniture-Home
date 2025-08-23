package com.example.FurnitureHome.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "store_settings", schema = "furniture")
public class StoreSetting {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "about_image_url")
    private String aboutImageUrl;

    @Lob
    @Column(name = "about_description")
    private String aboutDescription;

    @Lob
    @Column(name = "terms_and_conditions")
    private String termsAndConditions;

    @Column(name = "facebook_url")
    private String facebookUrl;

    @Column(name = "whatsapp_number", length = 20)
    private String whatsappNumber;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "second_phone_number", length = 20)
    private String secondPhoneNumber;

}