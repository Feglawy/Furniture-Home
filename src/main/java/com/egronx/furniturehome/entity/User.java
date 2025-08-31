package com.egronx.furniturehome.entity;


import jakarta.persistence.*;
import lombok.*;

import java.awt.print.Book;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name" , nullable = false)
    String name;
    @Column(name = "email" , nullable = false)
    String email;
    @Column(name = "password" , nullable = false)
    String password;
    @Column(name = "phone" , nullable = false)
    String phone;
    @Column(name = "location")
    String location;

    @Column(name = "reset_token")
    String resetToken;
    
    @Column(name = "reset_token_expiry")
    java.time.LocalDateTime resetTokenExpiry;

    @JoinColumn(name = "role_id" , nullable = false)
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    UserRole role;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE , orphanRemoval = true)
    Set<Enquiry> enquiries = new HashSet<>();


    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY , cascade = CascadeType.ALL , orphanRemoval = true)
    Set<Favorite> favorites = new HashSet<>();

    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY , cascade = CascadeType.ALL , orphanRemoval = true)
    Set<Review> reviews = new HashSet<>();

    @OneToOne(mappedBy = "user" , fetch = FetchType.EAGER , cascade = CascadeType.REMOVE , orphanRemoval = true)
    Cart cart;


    public void addFavorite(Favorite favorite){
        favorites.add(favorite);
    }

    public void addReview(Review review){
        reviews.add(review);
    }


}
