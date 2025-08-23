package com.example.FurnitureHome.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "enquiry", schema = "furniture", indexes = {
        @Index(name = "customer_id", columnList = "customer_id")
})
public class Enquiry {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @Lob
    @Column(name = "admin_reply")
    private String adminReply;

    @ColumnDefault("0")
    @Column(name = "closed")
    private Boolean closed;

}