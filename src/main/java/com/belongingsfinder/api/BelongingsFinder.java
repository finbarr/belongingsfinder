package com.belongingsfinder.api;

import java.util.Set;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.routing.Template;
import org.restlet.routing.TemplateRoute;
import org.restlet.routing.Variable;
import org.restlet.service.Service;

import com.belongingsfinder.api.framework.FilterFactory;
import com.belongingsfinder.api.framework.FinderFactory;
import com.belongingsfinder.api.modules.AppModule;
import com.belongingsfinder.api.modules.DAOModule;
import com.belongingsfinder.api.modules.RestletModule;
import com.belongingsfinder.api.modules.ServiceModule;
import com.belongingsfinder.api.resource.BelongingModelServerResource;
import com.belongingsfinder.api.resource.BelongingModelsServerResource;
import com.belongingsfinder.api.resource.CategoryModelServerResource;
import com.belongingsfinder.api.resource.CategoryModelsServerResource;
import com.belongingsfinder.api.resource.PagingBelongingModelServerResource;
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

		TemplateRoute belongings = apiv1.attach("/belongings/{number}",
				finderFactory.createFinder(BelongingModelsServerResource.class));

		belongings.getTemplate().getVariables().put("number", new Variable(Variable.TYPE_DIGIT, "0", false, false));

		apiv1.attach("/belongings/id/{id}",
				uuidFilterFactory.createFilter(finderFactory.createFinder(BelongingModelServerResource.class)));

		TemplateRoute belongingPager = apiv1.attach("/belongings/{type}/{number}/{offset}",
				finderFactory.createFinder(PagingBelongingModelServerResource.class));

		belongingPager.getTemplate().getVariables().put("number", new Variable(Variable.TYPE_DIGIT));
		belongingPager.getTemplate().getVariables().put("offset", new Variable(Variable.TYPE_DIGIT));

		apiv1.attach("/category", finderFactory.createFinder(CategoryModelsServerResource.class));
		apiv1.attach("/category/id/{id}",
				uuidFilterFactory.createFilter(finderFactory.createFinder(CategoryModelServerResource.class)));

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
