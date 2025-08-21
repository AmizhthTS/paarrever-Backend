package com.paarr.exception;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
public class ErrorResponseDTO implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	
	private Integer errorCode;
	private String errorMessage;
}