package com.belongingsfinder.api.framework;

import org.restlet.Restlet;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Filter;

import com.google.inject.Provider;

public abstract class AbstractFilterFactory implements FilterFactory {

	private final Provider<Filter> filterProvider;
	private final FinderFactory finderFactory;

	public AbstractFilterFactory(Provider<Filter> filterProvider, FinderFactory finderFactory) {
		this.filterProvider = filterProvider;
		this.finderFactory = finderFactory;
	}

	public Filter createFilter(Class<? extends ServerResource> targetClass) {
		return createFilter(finderFactory.createFinder(targetClass));
	}

	public Filter createFilter(Restlet next) {
		Filter filter = filterProvider.get();
		filter.setNext(next);
		return filter;
	}

}