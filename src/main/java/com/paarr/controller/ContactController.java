package com.paarr.controller;
import com.paarr.dto.ContactListRequestDTO;
import com.paarr.dto.ContactListResponseDTO;
import com.paarr.dto.ContactMessageDTO;
import com.paarr.dto.ContactPageResponseDTO;
import com.paarr.dto.ResponseDTO;
import com.paarr.service.implementation.ContactMessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/contact")
@CrossOrigin(origins = "*")
public class ContactController {
	
	

	

	    @Autowired
	    private ContactMessageServiceImpl contactMessageServiceImplservice;
	    
	    @Autowired
	    private ContactMessageServiceImpl contactMessageServiceImpl;

//	    @PostMapping
//	    public ResponseEntity<String> saveMessage(@RequestBody ContactMessageDTO contactMessageDTO) {
//	        String result = contactMessageServiceImplservice.saveMessage(contactMessageDTO);
//	        return ResponseEntity.ok(result);
//	    }
	    @PostMapping
	    public ResponseEntity<ResponseDTO> saveMessage(@RequestBody ContactMessageDTO contactMessageDTO) {
	        ResponseDTO result = contactMessageServiceImplservice.saveMessage(contactMessageDTO);
	        return ResponseEntity.ok(result);
	    }
//	    @PostMapping("/list")
//	    public ResponseEntity<Page<ContactListResponseDTO>> getContactList(@RequestBody ContactListRequestDTO request) {
//	        Page<ContactListResponseDTO> result = contactMessageServiceImpl.getContactList(request);
//	        return ResponseEntity.ok(result);
//	    }
	    @PostMapping("/list")
	    public ResponseEntity<ContactPageResponseDTO> getContacts(@RequestBody ContactListRequestDTO request) {
	        return ResponseEntity.ok(contactMessageServiceImpl.getContactList(request));
	    }

	


}
