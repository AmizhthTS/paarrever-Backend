package com.paarr.controller;
import com.paarr.dto.ContactMessageDTO;
import com.paarr.service.implementation.ContactMessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/contact")
@CrossOrigin(origins = "*")
public class ContactController {
	
	

	

	    @Autowired
	    private ContactMessageServiceImpl contactMessageServiceImplservice;

	    @PostMapping
	    public ResponseEntity<String> saveMessage(@RequestBody ContactMessageDTO contactMessageDTO) {
	        String result = contactMessageServiceImplservice.saveMessage(contactMessageDTO);
	        return ResponseEntity.ok(result);
	    }
	


}
