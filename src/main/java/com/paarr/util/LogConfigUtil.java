package com.paarr.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:logconfig.properties")
public class LogConfigUtil {

	@Value("#{${logrequest}}")
	private Map<String, Boolean> requestList;

	public Boolean isLogRequest(String endpoint) {
		return requestList.get(endpoint) != null ? requestList.get(endpoint) : false;
	}

	@Value("#{${logresponse}}")
	private Map<String, Boolean> responseList;

	public Boolean isLogResponse(String endpoint) {
		return requestList.get(endpoint) != null ? requestList.get(endpoint) : false;
	}

}