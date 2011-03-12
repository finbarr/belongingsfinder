package com.belongingsfinder.api.framework;

import org.restlet.resource.Finder;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * @author Finbarr
 * 
 */
@Singleton
public class GuiceFinderFactory implements FinderFactory {

	private final Injector injector;

	@Inject
	public GuiceFinderFactory(Injector injector) {
		this.injector = injector;
	}

	public Finder createFinder(Class<? extends ServerResource> targetClass) {
		return new GuiceFinder(targetClass, injector);
	}

}
