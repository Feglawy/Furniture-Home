package com.egronx.furniturehome.entity;


import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "store_settings")
public class StoreSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;
    @Column(name = "logo_url")
    String logoUrl;
    @Column(name = "about_image_url")
    String aboutImageUrl;
    @Column(name = "about_description")
    String aboutDescription;
    @Column(name = "terms_and_Conditions")
    String termsAndConditions;
    @Column(name = "facebook_url")
    String facebookUrl;
    @Column(name = "whatsapp_number")
    String whatsappNumber;
    @Column(name = "phone_number")
    String phoneNumber;
    @Column(name = "second_phone_number")
    String secondPhoneNumber;

}
