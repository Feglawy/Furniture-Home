package com.example.FurnitureHome.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "cart", schema = "furniture", uniqueConstraints = {
        @UniqueConstraint(name = "user_id", columnNames = {"user_id"})
})
public class Cart {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}