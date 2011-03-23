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

import com.amazonaws.services.s3.model.Region;
import com.belongingsfinder.api.framework.FilterFactory;
import com.belongingsfinder.api.framework.FinderFactory;
import com.belongingsfinder.api.modules.AWSModule.BucketName;
import com.belongingsfinder.api.modules.BelongingsFinderModules;
import com.belongingsfinder.api.resource.BelongingModelCountServerResource;
import com.belongingsfinder.api.resource.BelongingModelServerResource;
import com.belongingsfinder.api.resource.BelongingModelsServerResource;
import com.belongingsfinder.api.resource.BelongingSearchServerResource;
import com.belongingsfinder.api.resource.CategoryModelServerResource;
import com.belongingsfinder.api.resource.CategoryModelsServerResource;
import com.belongingsfinder.api.resource.ChildrenCategoryModelServerResource;
import com.belongingsfinder.api.resource.MobileBelongingModelServerResource;
import com.belongingsfinder.api.resource.PagingBelongingModelServerResource;
import com.belongingsfinder.api.resource.RandomBelongingModelServerResource;
import com.belongingsfinder.api.resource.StuffServerResource;
import com.belongingsfinder.api.search.SearchIndexer;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.google.inject.persist.PersistService;

/**
 * @author finbarr
 * 
 *         http://finbarrtaylor.com
 * 
 */
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
	@Named("type")
	private FilterFactory typeFilterFactory;

	@Inject
	private PersistService persistService;

	@Inject
	private SearchIndexer indexer;

	@Inject
	private Set<Service> services;

	private final Region region;
	private final BucketName bucket;

	public BelongingsFinder(Region region, BucketName bucket) {
		this.region = region;
		this.bucket = bucket;
	}

	public static void main(String[] args) throws Exception {
		Component c = new Component();
		c.getServers().add(Protocol.HTTP, 8182);
		c.getDefaultHost().attach(new BelongingsFinder(Region.US_West, BucketName.TEST));
		c.start();
	}

	@Override
	public Restlet createInboundRoot() {
		Router apiv1 = routerProvider.get();

		apiv1.attach("/belongings", BelongingModelsServerResource.class);

		TemplateRoute belongingsCount = apiv1.attach("/belongings/count/{type}",
				typeFilterFactory.createFilter(finderFactory.createFinder(BelongingModelCountServerResource.class)));
		belongingsCount.getTemplate().getVariables().put("type", new Variable(Variable.TYPE_ALPHA));

		TemplateRoute randomBelongings = apiv1.attach("/belongings/random/{number}",
				RandomBelongingModelServerResource.class);
		randomBelongings.getTemplate().getVariables().put("number", new Variable(Variable.TYPE_DIGIT));

		apiv1.attach("/belongings/id/{id}",
				uuidFilterFactory.createFilter(finderFactory.createFinder(BelongingModelServerResource.class)));

		TemplateRoute belongingsPager = apiv1.attach("/belongings/{type}/{number}/{offset}",
				typeFilterFactory.createFilter(finderFactory.createFinder(PagingBelongingModelServerResource.class)));
		belongingsPager.getTemplate().getVariables().put("number", new Variable(Variable.TYPE_DIGIT));
		belongingsPager.getTemplate().getVariables().put("offset", new Variable(Variable.TYPE_DIGIT));
		belongingsPager.getTemplate().getVariables().put("type", new Variable(Variable.TYPE_ALPHA));

		apiv1.attach("/belongings/search", BelongingSearchServerResource.class);

		apiv1.attach("/categories", CategoryModelsServerResource.class);
		apiv1.attach("/categories/id/{id}",
				uuidFilterFactory.createFilter(finderFactory.createFinder(CategoryModelServerResource.class)));
		apiv1.attach("/categories/children/{id}",
				uuidFilterFactory.createFilter(finderFactory.createFinder(ChildrenCategoryModelServerResource.class)));

		apiv1.attach("/mobile", MobileBelongingModelServerResource.class);

		Router app = routerProvider.get();
		app.attach(v1, apiv1, Template.MODE_STARTS_WITH);
		app.attach("/stuff", StuffServerResource.class);

		return app;
	}

	@Override
	public synchronized void start() throws Exception {
		Injector injector = Guice.createInjector(new BelongingsFinderModules("belongingsfinder", bucket, region));
		injector.injectMembers(this);
		persistService.start();
		indexer.index();
		registerServices(services);
		super.start();
	}

	@Override
	public synchronized void stop() throws Exception {
		persistService.stop();
		super.stop();
	}

	private void registerServices(Set<Service> services) {
		for (Service service : services) {
			super.getServices().add(service);
		}
	}

}
