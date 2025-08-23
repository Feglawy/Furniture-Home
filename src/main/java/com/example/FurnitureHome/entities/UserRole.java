package com.example.FurnitureHome.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_role", schema = "furniture")
public class UserRole {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

}