package com.belongingsfinder.api.modules;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class AWSModule extends AbstractModule {

	private final BucketName bucket;

	public AWSModule(BucketName bucket) {
		this.bucket = bucket;
	}

	@Override
	protected void configure() {
		bind(BucketName.class).toInstance(bucket);
		bind(AWSCredentials.class).toInstance(
				new BasicAWSCredentials("AKIAIJ66JWWVTLJRVN4A", "teCb2V5lq8ZLE5XCPOXZGgiUuV623DE6c93MV6vQ"));
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

		LIVE("belongingsfinder-live"), TEST("belongingsfinder-test");

		private final String name;

		private BucketName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

	}

}