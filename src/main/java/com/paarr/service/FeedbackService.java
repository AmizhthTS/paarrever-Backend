package com.paarr.service;

import com.paarr.dto.FeedbackDTO;
import com.paarr.dto.FeedbackPageDTO;
import com.paarr.dto.ResponseDTO;

public interface FeedbackService {

    ResponseDTO save(FeedbackDTO dto);

    FeedbackDTO get(Long id);

    ResponseDTO delete(Long id);

    FeedbackPageDTO list(FeedbackPageDTO dto);
}