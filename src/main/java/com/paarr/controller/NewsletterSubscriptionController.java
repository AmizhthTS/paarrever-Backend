package com.paarr.controller;


import com.paarr.dto.*;
import com.paarr.service.NewsletterSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/newsletter/subscribe") 
@CrossOrigin(origins = "*")
public class NewsletterSubscriptionController {

    @Autowired
    private NewsletterSubscriptionService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> subscribe(@RequestBody NewsletterSubscriptionDTO dto) {
        ResponseDTO responseDTO = service.subscribe(dto);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewsletterSubscriptionPageDTO> list(@RequestBody NewsletterSubscriptionPageDTO newsletterSubscriptionPageDTOdto) {
    	newsletterSubscriptionPageDTOdto = service.list(newsletterSubscriptionPageDTOdto);
        return new ResponseEntity<>(newsletterSubscriptionPageDTOdto, HttpStatus.OK);
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewsletterSubscriptionDTO> get(@RequestParam long id) {
        NewsletterSubscriptionDTO newsletterSubscriptionDTO = service.get(id);
        return new ResponseEntity<>(newsletterSubscriptionDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/unsubscribe", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> unsubscribe(@RequestParam long id) {
        ResponseDTO responseDTO = service.unsubscribe(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
