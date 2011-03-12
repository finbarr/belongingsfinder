package com.belongingsfinder.api.framework;

import org.restlet.routing.Filter;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * @author Finbarr
 * 
 */
@Singleton
public class UUIDFilterFactory extends AbstractFilterFactory {

	@Inject
	public UUIDFilterFactory(@Named("uuid") Provider<Filter> filterProvider, FinderFactory finderFactory) {
		super(filterProvider, finderFactory);
	}

}
