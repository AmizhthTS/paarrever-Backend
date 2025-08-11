package com.paarr.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "newsletter_subscriptions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsletterSubscriptionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private Boolean active;
}
