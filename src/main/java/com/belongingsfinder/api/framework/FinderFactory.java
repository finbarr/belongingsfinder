package com.belongingsfinder.api.framework;

import org.restlet.resource.Finder;
import org.restlet.resource.ServerResource;

/**
 * @author Finbarr
 * 
 */
public interface FinderFactory {

	public Finder createFinder(Class<? extends ServerResource> targetClass);

}
