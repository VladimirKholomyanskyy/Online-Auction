package com.tinyauction.filestorage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@Repository
public class AwsS3CloudAccessImpl implements AwsS3CloudAccess {
	
	private static final Logger LOGGER = Logger.getLogger(AwsS3CloudAccessImpl.class);
	@Autowired
	private AmazonS3 s3Client;
	
	@Value("${aws_namecard_bucket}")
	private String bucket;

	@Value("${url_life_duration}")
	private long urlLifeDuration;
	
	@Value("${default_images_folder}")
	private String defaultImagesFolder;
	
	@Value("${image_not_available}")
	private String imageNotAvailable;
	

	@Override
	public void deleteFile(String fileName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public File downloadFile(String key) {
		S3Object s3Object = s3Client.getObject(bucket, key);
		S3ObjectInputStream inputStream = s3Object.getObjectContent();
		File tmp = null;
		try {
			tmp = File.createTempFile("", key);
			FileUtils.copyInputStreamToFile(inputStream, tmp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void uploadFile(String key, InputStream input, ObjectMetadata metadata) {
		System.out.println("UPLOADING FILE: "+ metadata.getUserMetaDataOf("filename"));
		s3Client.putObject(bucket, key, input, metadata);
		
	}

	@Override
	public String generatePresignedUrl(String key) {
		LOGGER.info("generatePresignedUrl: path = "+key);
		
		long currentTime = System.currentTimeMillis();
		long expTimeMillis = currentTime + urlLifeDuration ;
		LOGGER.info("expTimeMillis = "+expTimeMillis);
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket,key)
				.withMethod(HttpMethod.GET)
				.withExpiration(new Date(expTimeMillis));
		URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
		LOGGER.info("URL = "+url.toString());
		return url.toString();
	}

	@Override
	public String getNoImageAvailableKey() {
		
		return defaultImagesFolder+"/"+imageNotAvailable;
	}
	
	
}
