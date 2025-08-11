package com.paarr.repository;


import com.paarr.entity.NewsletterSubscriptionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsletterSubscriptionRepository extends JpaRepository<NewsletterSubscriptionModel, Long> {
    NewsletterSubscriptionModel findByIdAndActive(Long id, Boolean active);
    NewsletterSubscriptionModel findByEmailAndActive(String email, Boolean active);
    Page<NewsletterSubscriptionModel> findByEmailContainsIgnoreCaseAndActive(String email, Boolean active, Pageable pageable);
}
