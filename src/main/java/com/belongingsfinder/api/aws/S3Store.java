package com.belongingsfinder.api.aws;

import java.io.InputStream;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Region;
import com.belongingsfinder.api.modules.AWSModule.BucketName;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class S3Store {

	private final AmazonS3 client;
	private final BucketName bucket;
	private final Region region;

	@Inject
	private S3Store(AmazonS3 client, BucketName bucket, Region region) {
		this.client = client;
		this.bucket = bucket;
		this.region = region;
	}

	public void delete(String key) {
		client.deleteObject(bucket.getName(), key);
	}

	public S3File store(String key, InputStream inputStream, ObjectMetadata meta, CannedAccessControlList access)
			throws AmazonClientException, AmazonServiceException {
		PutObjectRequest request = new PutObjectRequest(bucket.getName(), key, inputStream, meta);
		request.setCannedAcl(access);
		client.putObject(request);
		return new S3File(bucket, key, region);
	}

}
