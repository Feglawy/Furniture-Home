package com.example.FurnitureHome.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "order_status_changes", schema = "furniture", indexes = {
        @Index(name = "order_id", columnList = "order_id")
})
public class OrderStatusChange {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Lob
    @Column(name = "status", nullable = false)
    private String status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

}