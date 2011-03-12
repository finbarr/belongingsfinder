package com.belongingsfinder.api.framework;

import org.restlet.resource.Finder;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * @author Finbarr
 * 
 */
public class GuiceRouter extends Router {

	private final Injector injector;

	@Inject
	public GuiceRouter(Injector injector) {
		this.injector = injector;
	}

	@Override
	public Finder createFinder(Class<?> targetClass) {
		if (ServerResource.class.isAssignableFrom(targetClass)) {
			return new GuiceFinder(targetClass, injector);
		}
		return super.createFinder(targetClass);
	}

}
