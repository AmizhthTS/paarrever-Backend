package com.paarr.dto;



import lombok.Data;
import java.util.List;

@Data
public class NewsletterSubscriptionPageDTO {
    private int pageNumber;
    private int listSize;
    private String searchString;
    private long count;
    private int totalPages;
    private List<NewsletterSubscriptionDTO> subscriptions;
    private ResponseDTO response;
}

