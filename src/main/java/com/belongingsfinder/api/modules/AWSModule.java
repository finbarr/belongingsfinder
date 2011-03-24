package com.belongingsfinder.api.modules;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Region;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class AWSModule extends AbstractModule {

	private final BucketName bucket;
	private final Region s3region;

	public AWSModule(BucketName bucket, Region s3region) {
		this.bucket = bucket;
		this.s3region = s3region;
	}

	@Override
	protected void configure() {
		bind(BucketName.class).toInstance(bucket);
		bind(AWSCredentials.class).toInstance(
				new BasicAWSCredentials("AKIAIJ66JWWVTLJRVN4A", "teCb2V5lq8ZLE5XCPOXZGgiUuV623DE6c93MV6vQ"));
		bind(Region.class).toInstance(s3region);
		try {
			bind(AmazonS3.class).toConstructor(AmazonS3Client.class.getConstructor(AWSCredentials.class)).in(
					Singleton.class);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	public enum BucketName {

		LIVE("belongingsfinder-live"), DEV("belongingsfinder-dev");

		private final String name;

		private BucketName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

	}

}
