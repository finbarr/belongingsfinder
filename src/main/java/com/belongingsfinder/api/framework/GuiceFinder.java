package com.belongingsfinder.api.framework;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Finder;
import org.restlet.resource.ServerResource;

import com.google.inject.Injector;

/**
 * @author Finbarr
 * 
 */
public class GuiceFinder extends Finder {

	private final Injector injector;

	public GuiceFinder(Class<?> targetClass, Injector injector) {
		super(null, targetClass);
		this.injector = injector;
	}

	@Override
	public ServerResource create(Class<? extends ServerResource> targetClass, Request request, Response response) {
		return injector.getInstance(targetClass);
	}

}
