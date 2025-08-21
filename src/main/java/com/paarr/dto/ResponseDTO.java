package com.paarr.dto;
import com.paarr.entity.CategoryModel;
import com.paarr.exception.ErrorResponseDTO;

import lombok.Data;
@Data
public class ResponseDTO {
	

	    private String responseStatus;
	    private String responseMessage;
	    private Object response;
		private ErrorResponseDTO errorResponseDTO;

}
//public class ResponseDTO<T> {
//    private String responseStatus;
//    private String responseMessage;
//    private T response;
//}

