package com.belongingsfinder.api.modules;

import com.amazonaws.services.s3.model.Region;
import com.belongingsfinder.api.modules.AWSModule.BucketName;
import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;

public class BelongingsFinderModules extends AbstractModule {

	private final String persistenceUnit;
	private final BucketName bucketName;
	private final Region region;

	public BelongingsFinderModules(String persistenceUnit, BucketName bucketName, Region region) {
		this.persistenceUnit = persistenceUnit;
		this.bucketName = bucketName;
		this.region = region;
	}

	@Override
	protected void configure() {
		install(new AppModule());
		install(new AWSModule(bucketName, region));
		install(new BelongingModelPagerModule());
		install(new DAOModule());
		install(new FileItemHandlerModule());
		install(new JpaPersistModule(persistenceUnit));
		install(new ModelModule());
		install(new RestletModule());
		install(new ServiceModule());
	}

}
