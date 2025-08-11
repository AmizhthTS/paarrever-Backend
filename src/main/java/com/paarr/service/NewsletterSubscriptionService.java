package com.paarr.service;


import com.paarr.dto.NewsletterSubscriptionDTO;
import com.paarr.dto.NewsletterSubscriptionPageDTO;
import com.paarr.dto.ResponseDTO;

public interface NewsletterSubscriptionService {
    ResponseDTO subscribe(NewsletterSubscriptionDTO dto);
    NewsletterSubscriptionPageDTO list(NewsletterSubscriptionPageDTO dto);
    NewsletterSubscriptionDTO get(long id);
    ResponseDTO unsubscribe(long id);
}

