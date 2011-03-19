package com.belongingsfinder.api.modules;

import com.belongingsfinder.api.modules.AWSModule.BucketName;
import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;

public class BelongingsFinderModules extends AbstractModule {

	private final String persistenceUnit;
	private final BucketName bucketName;

	public BelongingsFinderModules(String persistenceUnit, BucketName bucketName) {
		this.persistenceUnit = persistenceUnit;
		this.bucketName = bucketName;
	}

	@Override
	protected void configure() {
		install(new AppModule());
		install(new AWSModule(bucketName));
		install(new BelongingModelPagerModule());
		install(new DAOModule());
		install(new FileItemHandlerModule());
		install(new JpaPersistModule(persistenceUnit));
		install(new RestletModule());
		install(new ServiceModule());
	}

}
