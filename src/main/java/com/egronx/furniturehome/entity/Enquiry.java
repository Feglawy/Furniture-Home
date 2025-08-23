package com.egronx.furniturehome.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "enquiry")
public class Enquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content" , nullable = false)
    private String content;
    @Column(name = "admin_reply" , nullable = false)
    private String adminReply;
    @Column(name = "closed")
    private boolean closed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id" , nullable = false)
    private User user;
}
