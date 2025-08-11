package com.paarr.service.implementation;

import com.paarr.dto.*;
import com.paarr.entity.NewsletterSubscriptionModel;
import com.paarr.repository.NewsletterSubscriptionRepository;
import com.paarr.service.NewsletterSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsletterSubscriptionServiceImpl implements NewsletterSubscriptionService {

    @Autowired
    NewsletterSubscriptionRepository newsletterSubscriptionRepository;

   
    public ResponseDTO subscribe(NewsletterSubscriptionDTO newsletterSubscriptionDTO) {
        NewsletterSubscriptionModel existing = newsletterSubscriptionRepository.findByEmailAndActive(newsletterSubscriptionDTO.getEmail(), true);
        if (existing != null) {
            throw new RuntimeException("Email already subscribed");
        }

        NewsletterSubscriptionModel newsletterSubscriptionModel = new NewsletterSubscriptionModel();
        newsletterSubscriptionModel.setEmail(newsletterSubscriptionDTO.getEmail());
        newsletterSubscriptionModel.setActive(true);
        newsletterSubscriptionRepository.save(newsletterSubscriptionModel);

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseStatus("Success");
        responseDTO.setResponseMessage("Subscribed successfully");
        return responseDTO;
    }

   
    public NewsletterSubscriptionPageDTO list(NewsletterSubscriptionPageDTO newsletterSubscriptionPageDTO) {
        Pageable paging = PageRequest.of(
        		newsletterSubscriptionPageDTO.getPageNumber() > 0 ? newsletterSubscriptionPageDTO.getPageNumber() - 1 : 0,
        				newsletterSubscriptionPageDTO.getListSize() > 0 ? newsletterSubscriptionPageDTO.getListSize() : 25,
                Sort.by("email").ascending()
        );

        Page<NewsletterSubscriptionModel> page;
        if (newsletterSubscriptionPageDTO.getSearchString() != null && !newsletterSubscriptionPageDTO.getSearchString().isEmpty()) {
            page = newsletterSubscriptionRepository.findByEmailContainsIgnoreCaseAndActive(newsletterSubscriptionPageDTO.getSearchString(), true, paging);
        } else {
            page = newsletterSubscriptionRepository.findAll(paging);
        }

        List<NewsletterSubscriptionDTO> list = page.stream()
                .map(this::constructResponse)
                .collect(Collectors.toList());

        newsletterSubscriptionPageDTO.setSubscriptions(list);
        newsletterSubscriptionPageDTO.setCount(page.getTotalElements());
        newsletterSubscriptionPageDTO.setTotalPages(page.getTotalPages());

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseStatus("Success");
        responseDTO.setResponseMessage("List fetched");
      
		newsletterSubscriptionPageDTO.setResponse(responseDTO);

        return newsletterSubscriptionPageDTO;
    }

    
    public NewsletterSubscriptionDTO get(long id) {
        NewsletterSubscriptionModel newsletterSubscriptionModel = newsletterSubscriptionRepository.findByIdAndActive(id, true);
        if (newsletterSubscriptionModel == null) {
            throw new RuntimeException("Subscriber not found");
        }
        return constructResponse(newsletterSubscriptionModel);
    }

   
    public ResponseDTO unsubscribe(long id) {
        NewsletterSubscriptionModel newsletterSubscriptionModel = newsletterSubscriptionRepository.findByIdAndActive(id, true);
        if (newsletterSubscriptionModel == null) {
            throw new RuntimeException("Subscriber not found");
        }
        newsletterSubscriptionModel.setActive(false);
        newsletterSubscriptionRepository.save(newsletterSubscriptionModel);

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseStatus("Success");
        responseDTO.setResponseMessage("Unsubscribed successfully");
        return responseDTO;
    }

    private NewsletterSubscriptionDTO constructResponse(NewsletterSubscriptionModel model) {
        NewsletterSubscriptionDTO newsletterSubscriptionDTO = new NewsletterSubscriptionDTO();
        newsletterSubscriptionDTO.setId(model.getId());
        newsletterSubscriptionDTO.setEmail(model.getEmail());
        return newsletterSubscriptionDTO;
    }
}
