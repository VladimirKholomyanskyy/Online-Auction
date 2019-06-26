package com.tinyauction.filestorage;

import java.io.File;
import java.io.InputStream;

import com.amazonaws.services.s3.model.ObjectMetadata;

public interface AwsS3CloudAccess {

	void uploadFile(String key, InputStream input, ObjectMetadata metadata);
	
	void deleteFile(String fileName);
	
	File downloadFile(String downloadsDirectory);
	
	String generatePresignedUrl(String path);
	
	String getNoImageAvailableKey();
}
