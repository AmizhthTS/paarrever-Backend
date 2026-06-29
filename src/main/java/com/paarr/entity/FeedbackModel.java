package com.paarr.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "feedback")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean active;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    private String branchName;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> boughtItems;

    private Integer menuRating;
    private Integer foodRating;
    private Integer staffRating;
    private Integer serviceRating;

    @Column(name = "feedback_message",columnDefinition = "TEXT")
    private String feedbackMessage;
    @Column(name = "feedbacktype",columnDefinition = "TEXT")
    private String feedbacktype;

    private String image;
    
}