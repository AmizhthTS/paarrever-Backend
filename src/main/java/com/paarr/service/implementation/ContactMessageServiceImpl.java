package com.paarr.service.implementation;
import com.paarr.dto.ContactMessageDTO;
import com.paarr.entity.ContactMessageModel;
import com.paarr.repository.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactMessageServiceImpl {
	
	
	    @Autowired
	    private ContactMessageRepository contactMessageRepository;

	    public String saveMessage(ContactMessageDTO contactMessageDTO) {
	        ContactMessageModel entity = new ContactMessageModel();
	        entity.setFullName(contactMessageDTO.getFullName());
	        entity.setEmailAddress(contactMessageDTO.getEmailAddress());
	        entity.setSubject(contactMessageDTO.getSubject());
	        entity.setMessage(contactMessageDTO.getMessage());

	        contactMessageRepository .save(entity);
	        return "Message saved successfully";
	    }
	

}
