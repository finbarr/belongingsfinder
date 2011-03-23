package com.belongingsfinder.api.modules;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * @author finbarr
 * 
 */
public class AppModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(String.class).annotatedWith(Names.named("v1")).toInstance("/1.0");
		// TODO clean this up on startup
		bind(String.class).annotatedWith(Names.named("temp")).toInstance("/bf-temp");
		bind(ExecutorService.class).toInstance(Executors.newCachedThreadPool());
	}

}
