package com.belongingsfinder.api.aws;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.amazonaws.services.s3.model.Region;
import com.belongingsfinder.api.modules.AWSModule.BucketName;

@Embeddable
public class S3File implements Serializable {

	private static final long serialVersionUID = -128435986775490232L;
	private static final String http = "http://";
	private static final String s3 = ".amazonaws.com/";

	@Enumerated(EnumType.STRING)
	private BucketName bucket;
	private String fileKey;
	@Enumerated(EnumType.STRING)
	private Region region;

	public S3File() {
	}

	public S3File(BucketName bucket, String key, Region region) {
		this.bucket = bucket;
		fileKey = key;
		this.region = region;
	}

	public S3File(String imageUrl) {
		throw new IllegalStateException("urgently need to fix this");
	}

	public String getAWSUrl() {
		return new StringBuilder(S3File.http).append(bucket.getName()).append(".").append(region.toString())
				.append(S3File.s3).append(fileKey).toString();
	}

	public BucketName getBucket() {
		return bucket;
	}

	public String getFileKey() {
		return fileKey;
	}

	public Region getRegion() {
		return region;
	}

	public void setBucket(BucketName bucket) {
		this.bucket = bucket;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

}
