package com.belongingsfinder.api;

import java.util.Set;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.routing.Template;
import org.restlet.service.Service;

import com.belongingsfinder.api.framework.FilterFactory;
import com.belongingsfinder.api.framework.FinderFactory;
import com.belongingsfinder.api.modules.AppModule;
import com.belongingsfinder.api.modules.DAOModule;
import com.belongingsfinder.api.modules.RestletModule;
import com.belongingsfinder.api.modules.ServiceModule;
import com.belongingsfinder.api.resource.BelongingModelServerResource;
import com.belongingsfinder.api.resource.BelongingModelsServerResource;
import com.belongingsfinder.api.resource.CategoriesServerResource;
import com.belongingsfinder.api.resource.CategoryModelServerResource;
import com.belongingsfinder.api.resource.CategoryModelsServerResource;
import com.belongingsfinder.api.resource.StuffServerResource;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class BelongingsFinder extends Application {

	@Inject
	private Provider<Router> routerProvider;

	@Inject
	private FinderFactory finderFactory;

	@Inject
	@Named("v1")
	private String v1;

	@Inject
	@Named("uuid")
	private FilterFactory uuidFilterFactory;

	@Inject
	private Set<Service> services;

	public static void main(String[] args) throws Exception {
		Component c = new Component();
		c.getServers().add(Protocol.HTTP, 8182);
		c.getDefaultHost().attach(new BelongingsFinder());
		c.start();
	}

	@Override
	public Restlet createInboundRoot() {
		Injector injector = Guice.createInjector(new AppModule(), new DAOModule(), new RestletModule(),
				new ServiceModule());
		injector.injectMembers(this);

		registerServices(services);

		Router apiv1 = routerProvider.get();

		apiv1.attach("/belongings", finderFactory.createFinder(BelongingModelsServerResource.class));
		apiv1.attach("/belongings/{id}",
				uuidFilterFactory.createFilter(finderFactory.createFinder(BelongingModelServerResource.class)));

		apiv1.attach("/category", finderFactory.createFinder(CategoryModelServerResource.class));
		apiv1.attach("/category/{id}",
				uuidFilterFactory.createFilter(finderFactory.createFinder(CategoryModelsServerResource.class)));

		apiv1.attach("/categories", finderFactory.createFinder(CategoriesServerResource.class));

		Router app = routerProvider.get();
		app.attach(v1, apiv1, Template.MODE_STARTS_WITH);
		app.attach("/stuff", StuffServerResource.class);

		return app;
	}

	private void registerServices(Set<Service> services) {
		for (Service service : services) {
			super.getServices().add(service);
		}
	}

}
