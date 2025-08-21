package com.paarr.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.paarr.exception.EcosystemException;
import com.paarr.exception.ErrorEnum;
import com.paarr.logging.LoggingServiceImpl;

@Component
public class AWSUtil {

	Logger logger = LoggerFactory.getLogger(LoggingServiceImpl.class);
	private final String className = "AWSUtil";
	@Autowired
	private AWSConfigUtil awsConfigUtil;

	@Value("${MaxFileSizeInMB}")
	private int MaxFileSizeInMB;

	@Value("${MaxFileSizeInMB50}")
	private int MaxFileSizeInMB50;

	public boolean checkFileSize(InputStream targetStream) throws Exception {
		return ((targetStream.available() / 1024.0) / 1024.0) < MaxFileSizeInMB;
	}

	public boolean checkFileSizev2(byte[] data) {
		return data.length <= (50L * 1024 * 1024); // 50 MB
	}

	public String saveFile(InputStream inputStream, String fileName, String fileFormat, String type) throws Exception {
		final String methodName = "saveFile";

		fileName = type + "-" + new Random().nextInt(9999) + "-" + fileName + "." + fileFormat;
		String fileType = "";
		if (fileFormat.equalsIgnoreCase("html"))
			fileType = "text/html";
		else if (fileFormat.equalsIgnoreCase("png"))
			fileType = "image/png";
		else if (fileFormat.equalsIgnoreCase("jpg"))
			fileType = "image/jpg";
		else if (fileFormat.equalsIgnoreCase("jpeg"))
			fileType = "image/jpeg";
		else if (fileFormat.equalsIgnoreCase("pdf"))
			fileType = "application/pdf";

		BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsConfigUtil.getAccessKey(),
				awsConfigUtil.getSecretKey());
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				.withRegion(Regions.valueOf(awsConfigUtil.getRegion())).build();

		byte[] contents = IOUtils.toByteArray(inputStream);
		InputStream stream = new ByteArrayInputStream(contents);
		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentLength(contents.length);
		meta.setContentType(fileType);

		String bucketName = getBucketName(type);

		try {
			s3Client.putObject(new PutObjectRequest(bucketName, fileName, stream, meta));
		} catch (Exception ex) {
			logger.error("Exception in " + className + " :: " + methodName + " :: " + ex.getMessage());
			throw new EcosystemException(ErrorEnum.FILE_UPLOAD_FAILED);
		} finally {
			inputStream.close();
		}

		return fileName;
	}

	public boolean copyAndTrashBucketObject(String awsS3FileName, String awsS3NewFileName, String type) {
		final String methodName = "copyAndTrashBucketObject";
		boolean status = false;
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsConfigUtil.getAccessKey(),
				awsConfigUtil.getSecretKey());
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				.withRegion(Regions.valueOf(awsConfigUtil.getRegion())).build();

		CopyObjectRequest copyObjectRequest = new CopyObjectRequest();

		String bucketName = getBucketName(type);

		copyObjectRequest.setSourceBucketName(bucketName);
		copyObjectRequest.setSourceKey(awsS3FileName);
		copyObjectRequest.setDestinationBucketName(awsConfigUtil.getTrashBucket());
		copyObjectRequest.setDestinationKey(awsS3NewFileName);

		try {
			s3Client.copyObject(copyObjectRequest);
			s3Client.deleteObject(new DeleteObjectRequest(bucketName, awsS3FileName));
			logger.info("copied and removed object :: " + awsS3FileName);
		} catch (Exception ex) {
			logger.error("Exception in " + className + " :: " + methodName + " :: " + ex.getMessage());
			throw new EcosystemException(ErrorEnum.FILE_UPLOAD_FAILED);
		}
		status = true;
		return status;
	}

	public boolean removeBucketObject(String fileName, String type) {
		boolean status = false;

		BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsConfigUtil.getAccessKey(),
				awsConfigUtil.getSecretKey());
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				.withRegion(Regions.valueOf(awsConfigUtil.getRegion())).build();

		String bucketName = getBucketName(type);

		s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
		status = true;

		return status;
	}

	public String getpreSignedFile(int hour, String filename, String type) {
		String preSignedFileUrl = "";

		BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsConfigUtil.getAccessKey(),
				awsConfigUtil.getSecretKey());
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				.withRegion(Regions.valueOf(awsConfigUtil.getRegion())).build();

		Date expiration = new Date();
		long expTimeMillis = Instant.now().toEpochMilli();
		long oneHour = 1000 * 60 * 60;
		long totalHour = oneHour * hour;
		expTimeMillis += totalHour;

		expiration.setTime(expTimeMillis);

		String bucketName = getBucketName(type);

		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, filename)
				.withMethod(HttpMethod.GET).withExpiration(expiration);
		URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
		preSignedFileUrl = url.toString();

		return preSignedFileUrl;
	}

	public byte[] getByteArrayFromImageS3Bucket(String fileName) throws IOException {
		InputStream in = getImageFromS3Bucket(awsConfigUtil.getAccessKey(), awsConfigUtil.getSecretKey(),
				awsConfigUtil.getRegion(), awsConfigUtil.getProfileBucket(), fileName).getObjectContent();
		byte[] bytes = IOUtils.toByteArray(in);
		in.close();
		return bytes;
	}

	private S3Object getImageFromS3Bucket(String awsAccessKeyId, String awsSecretKeyId, String awsRegion,
			String awsS3BucketName, String fileName) {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccessKeyId, awsSecretKeyId);
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				.withRegion(Regions.valueOf(awsRegion)).build();
		S3Object object = s3Client.getObject(new GetObjectRequest(awsS3BucketName, fileName));
		return object;
	}

	private String getBucketName(String type) {
		String bucketName = "";
		if (type.equalsIgnoreCase("category")) {
			bucketName = awsConfigUtil.getCategoryBucket();
		} else {
			bucketName = awsConfigUtil.getProfileBucket();
		}
		return bucketName;
	}
}