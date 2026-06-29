package com.paarr.dto;

import java.util.List;

import lombok.Data;
@Data
public class FeedbackPageDTO {

    private int pageNumber;
    private int listSize;
    private String searchString;
    private long count;
    private int totalPages;
	private long feedbackId;
	private String branchName;
    private String feedbackType;
    private List<FeedbackDTO> feedbacks;
    private ResponseDTO response;  


}