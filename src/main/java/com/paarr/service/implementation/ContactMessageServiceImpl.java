package com.paarr.service.implementation;
import com.paarr.dto.ContactListRequestDTO;
import com.paarr.dto.ContactListResponseDTO;
import com.paarr.dto.ContactMessageDTO;
import com.paarr.dto.ContactPageResponseDTO;
import com.paarr.dto.ResponseDTO;
import com.paarr.entity.ContactMessageModel;
import com.paarr.repository.ContactMessageRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ContactMessageServiceImpl {
	
	
	    @Autowired
	    private ContactMessageRepository contactMessageRepository;
	    
//	    @Autowired
//	    private ContactMessageServiceImpl contactMessageServiceImpl;

	    public ResponseDTO saveMessage(ContactMessageDTO contactMessageDTO) {
	        ContactMessageModel entity = new ContactMessageModel();
	        entity.setFullName(contactMessageDTO.getFullName());
	        entity.setEmailAddress(contactMessageDTO.getEmailAddress());
	        entity.setSubject(contactMessageDTO.getSubject());
	        entity.setMessage(contactMessageDTO.getMessage());
	        entity.setCheckbox(contactMessageDTO.isCheckbox());
	        entity.setPhoneNumber(contactMessageDTO.getPhoneNumber());

	        contactMessageRepository .save(entity);
//	        return "Message saved successfully";
	       // return new ResponseDTO("success", "Message saved successfully",null,null);
	        ResponseDTO dto = new ResponseDTO();
	        dto.setResponseStatus("success");
	        dto.setResponseMessage("Message saved successfully");
	        dto.setResponse(null);
	        dto.setErrorResponseDTO(null);

	        return dto;
	    }
//	    public Page<ContactListResponseDTO> getContactList(ContactListRequestDTO request) {
//	        Pageable pageable = PageRequest.of(request.getPageNumber() - 1, request.getListSize(), Sort.by("id").descending());
//
//	        Page<ContactMessageModel> page;
//	        if (request.getSearchString() != null && !request.getSearchString().isEmpty()) {
//	            page = contactMessageRepository.findByFullNameContainingIgnoreCaseOrEmailAddressContainingIgnoreCase(
//	                    request.getSearchString(),
//	                    request.getSearchString(),
//	                    pageable
//	            );
//	        } else {
//	            page = contactMessageRepository.findAll(pageable);
//	        }
//
//	        List<ContactListResponseDTO> dtoList = page.getContent().stream().map(contact -> {
//	            ContactListResponseDTO dto = new ContactListResponseDTO();
//	            dto.setId(contact.getId());
//	            dto.setFullName(contact.getFullName());
//	            dto.setEmailAddress(contact.getEmailAddress());
//	            dto.setSubject(contact.getSubject());
//	            dto.setMessage(contact.getMessage());
//	            return dto;
//	        }).collect(Collectors.toList());
//
//	        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
//	    }
	    public ContactPageResponseDTO getContactList(ContactListRequestDTO request) {
	        Pageable pageable = PageRequest.of(
	                request.getPageNumber() - 1,
	                request.getListSize(),
	                Sort.by("id").descending()
	        );

	        Page<ContactMessageModel> page;
	        if (request.getSearchString() != null && !request.getSearchString().isEmpty()) {
	            page = contactMessageRepository.findByFullNameContainingIgnoreCaseOrEmailAddressContainingIgnoreCase(
	                    request.getSearchString(),
	                    request.getSearchString(),
	                    pageable
	            );
	        } else {
	            page = contactMessageRepository.findAll(pageable);
	        }

	        List<ContactListResponseDTO> dtoList = page.getContent().stream().map(contact -> {
	            ContactListResponseDTO dto = new ContactListResponseDTO();
	            dto.setId(contact.getId());
	            dto.setFullName(contact.getFullName());
	            dto.setEmailAddress(contact.getEmailAddress());
	            dto.setSubject(contact.getSubject());
	            dto.setMessage(contact.getMessage());
	            dto.setPhoneNumber(contact.getPhoneNumber());
	            dto.setCheckbox(contact.isCheckbox());
	           
	            return dto;
	        }).collect(Collectors.toList());

	        // build response
	        ContactPageResponseDTO response = new ContactPageResponseDTO();
	        response.setPageNumber(request.getPageNumber());
	        response.setListSize(request.getListSize());
	        response.setSearchString(request.getSearchString());
	        //response.setCount(dtoList.size());
	        response.setCount(page.getTotalElements());
	        response.setTotalPages(page.getTotalPages());
	        response.setData(dtoList);

	        return response;
	    }

	

}
