package com.belongingsfinder.api.modules;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;

public class BelongingsFinderModules extends AbstractModule {

	private final String persistenceUnit;

	public BelongingsFinderModules(String persistenceUnit) {
		this.persistenceUnit = persistenceUnit;
	}

	@Override
	protected void configure() {
		install(new AppModule());
		install(new BelongingModelPagerModule());
		install(new DAOModule());
		install(new FileItemHandlerModule());
		install(new JpaPersistModule(persistenceUnit));
		install(new RestletModule());
		install(new ServiceModule());
	}

}
