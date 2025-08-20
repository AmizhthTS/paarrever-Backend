package com.paarr.dto;
import com.paarr.entity.CategoryModel;

import lombok.Data;
@Data
public class ResponseDTO {
	

	    private String responseStatus;
	    private String responseMessage;
	    private Object response;
	

}
//public class ResponseDTO<T> {
//    private String responseStatus;
//    private String responseMessage;
//    private T response;
//}

