package com.belongingsfinder.api.modules;

import org.restlet.routing.Filter;
import org.restlet.routing.Router;

import com.belongingsfinder.api.framework.BelongingTypeFilter;
import com.belongingsfinder.api.framework.BelongingTypeFilterFactory;
import com.belongingsfinder.api.framework.FilterFactory;
import com.belongingsfinder.api.framework.FinderFactory;
import com.belongingsfinder.api.framework.GuiceFinderFactory;
import com.belongingsfinder.api.framework.GuiceRouter;
import com.belongingsfinder.api.framework.UUIDFilter;
import com.belongingsfinder.api.framework.UUIDFilterFactory;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * @author finbarr
 * 
 */
public class RestletModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Router.class).to(GuiceRouter.class);
		bind(FinderFactory.class).to(GuiceFinderFactory.class);

		bind(FilterFactory.class).annotatedWith(Names.named("uuid")).to(UUIDFilterFactory.class);
		bind(Filter.class).annotatedWith(Names.named("uuid")).to(UUIDFilter.class);

		bind(FilterFactory.class).annotatedWith(Names.named("type")).to(BelongingTypeFilterFactory.class);
		bind(Filter.class).annotatedWith(Names.named("type")).to(BelongingTypeFilter.class);
	}

}
