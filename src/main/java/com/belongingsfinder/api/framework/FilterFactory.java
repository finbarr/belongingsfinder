package com.belongingsfinder.api.framework;

import org.restlet.Restlet;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Filter;

/**
 * @author finbarr
 * 
 */
public interface FilterFactory {

	public Filter createFilter(Class<? extends ServerResource> targetClass);

	public Filter createFilter(Restlet next);

}
