package com.paarr.dto;



import lombok.Data;

@Data
public class NewsletterSubscriptionDTO {
    private Long id;
    private String email;
    private ResponseDTO response;
}
