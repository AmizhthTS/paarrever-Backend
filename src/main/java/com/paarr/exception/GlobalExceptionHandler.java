package com.paarr.exception;

import java.time.ZonedDateTime;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.paarr.dto.ResponseDTO;
import com.paarr.entity.ErrorLogModel;
import com.paarr.repository.ErrorLogRepository;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@Value("${spring.mail.errorReceipients}")
	private String errorReceipients;
	@Autowired
	private ErrorLogRepository errorLogRepository;
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleResourceNotFound(ResourceNotFoundException ex) {
        ResponseDTO dto = new ResponseDTO();
        dto.setResponseStatus("Failed");
        dto.setResponseMessage(ex.getMessage());
        dto.setResponse(null);
        dto.setErrorResponseDTO(null);
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDTO> Exception(Exception exception, WebRequest webRequest, HandlerMethod handler) {
		ErrorResponseDTO errorResponse = new ErrorResponseDTO();
		errorResponse.setErrorCode(1000);
		errorResponse.setErrorMessage(
				"We're sorry, but it looks like something went wrong. Please try again later or reach us at tech@startuptn.in or 155343 or +914422256789");
		ResponseDTO response = new ResponseDTO();
		response.setResponseStatus("Error");
		response.setErrorResponseDTO(errorResponse);
		ResponseEntity<ResponseDTO> entity = new ResponseEntity<ResponseDTO>(response,
				HttpStatus.INTERNAL_SERVER_ERROR);

		String errorMessage = "";
		try {
			errorMessage = "General Exception  :: For User ::  :: On Controller :: "
					+ handler.getMethod().getDeclaringClass() + " :: At Method :: " + handler.getMethod().getName()
					+ " :: " + exception.getMessage();
		} catch (Exception e) {
			errorMessage = "General Exception :: On Controller :: " + handler.getMethod().getDeclaringClass()
					+ " :: At Method :: " + handler.getMethod().getName() + " :: " + exception.getMessage();
		}
		logger.error(errorMessage);

		ErrorLogModel errorLogModel = ErrorLogModel.builder().addressed(false).code("1000").message(errorMessage)
				.occurredtime(ZonedDateTime.now()).build();
		errorLogRepository.save(errorLogModel);

		HashMap<String, String> bodyValues = new HashMap<>();
		bodyValues.put("errorCode", errorLogModel.getCode());
		bodyValues.put("errorMessage", errorMessage);
		HashMap<String, String> encodedbodyValues = new HashMap<>();
		encodedbodyValues.put("errorCode", errorLogModel.getCode());
		encodedbodyValues.put("errorMessage", errorMessage);
		//emailUtil.sendEmail(errorReceipients, bodyValues, encodedbodyValues, "ADMIN_ERROR", "noreply");

		return entity;
	}

	@ExceptionHandler(EcosystemException.class)
	public ResponseEntity<ResponseDTO> ecosystemException(EcosystemException exception, WebRequest webRequest,
			HandlerMethod handler) {
		ErrorResponseDTO errorResponse = new ErrorResponseDTO();
		errorResponse.setErrorCode(exception.getErrorCode());
		errorResponse.setErrorMessage(exception.getMessage());
		ResponseDTO response = new ResponseDTO();
		response.setResponseStatus("Error");
		response.setErrorResponseDTO(errorResponse);
		ResponseEntity<ResponseDTO> entity = new ResponseEntity<ResponseDTO>(response,
				HttpStatus.INTERNAL_SERVER_ERROR);

		String errorMessage = "";
		try {
			errorMessage = "ecosystem Exception :: For User ::  :: On Controller :: "
					+ handler.getMethod().getDeclaringClass() + " :: At Method :: " + handler.getMethod().getName()
					+ " :: " + exception.getErrorCode() + " :: " + exception.getMessage();
		} catch (Exception e) {
			errorMessage = "ecosystem Exception :: On Controller :: " + handler.getMethod().getDeclaringClass()
					+ " :: At Method :: " + handler.getMethod().getName() + " :: " + exception.getErrorCode() + " :: "
					+ exception.getMessage();
		}
		logger.error(errorMessage);

		ErrorLogModel errorLogModel = ErrorLogModel.builder().addressed(false).code("" + exception.getErrorCode())
				.message(errorMessage).occurredtime(ZonedDateTime.now()).build();
		errorLogRepository.save(errorLogModel);

		if (exception.getErrorCode() == 100) {
			HashMap<String, String> bodyValues = new HashMap<>();
			bodyValues.put("errorCode", errorLogModel.getCode());
			bodyValues.put("errorMessage", errorMessage);
			HashMap<String, String> encodedbodyValues = new HashMap<>();
			encodedbodyValues.put("errorCode", errorLogModel.getCode());
			encodedbodyValues.put("errorMessage", errorMessage);
			//emailUtil.sendEmail(errorReceipients, bodyValues, encodedbodyValues, "ADMIN_ERROR", "noreply");

		}
		return entity;
	}

}
