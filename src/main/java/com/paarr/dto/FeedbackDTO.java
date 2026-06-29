package com.paarr.dto;

import java.util.List;

import lombok.Data;
@Data
public class FeedbackDTO {

    private Long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    private String branchName;

    private List<String> boughtItems;

    private Integer menuRating;
    private Integer foodRating;
    private Integer staffRating;
    private Integer serviceRating;

    private String feedbackMessage;
    private String feedbackType;

    private byte[] image;
    private String imageName;

}