package com.belongingsfinder.api.framework;

import org.restlet.routing.Filter;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * @author finbarr
 * 
 */
@Singleton
public class BelongingTypeFilterFactory extends AbstractFilterFactory {

	@Inject
	public BelongingTypeFilterFactory(@Named("type") Provider<Filter> filterProvider, FinderFactory finderFactory) {
		super(filterProvider, finderFactory);
	}

}
