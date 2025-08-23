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

    @JoinColumn(name = "role_id" , nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    UserRole role;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE , orphanRemoval = true)
    Set<Enquiry> enquiries = new HashSet<>();


    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY , cascade = CascadeType.REMOVE , orphanRemoval = true)
    Set<Favorite> favorites = new HashSet<>();

    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY , cascade = CascadeType.REMOVE , orphanRemoval = true)
    Set<Review> reviews = new HashSet<>();

    @OneToOne(mappedBy = "user" , fetch = FetchType.EAGER , cascade = CascadeType.ALL , orphanRemoval = true)
    Cart cart;


}
