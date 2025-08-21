package com.paarr.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:awsconfig.properties")
public class AWSConfigUtil {

	@Value("#{${accesskey}}")
	private String accessKey;

	public String getAccessKey() {
		return accessKey;
	}

	@Value("#{${secretkey}}")
	private String secretkey;

	public String getSecretKey() {
		return secretkey;
	}

	@Value("#{${region}}")
	private String region;

	public String getRegion() {
		return region;
	}

	@Value("#{${profile.bucketname}}")
	private String profileBucket;

	public String getProfileBucket() {
		return profileBucket;
	}

	@Value("#{${category.bucketname}}")
	private String categoryBucket;

	public String getCategoryBucket() {
		return categoryBucket;
	}

	@Value("#{${trash.bucketname}}")
	private String trashBucket;

	public String getTrashBucket() {
		return trashBucket;
	}

}