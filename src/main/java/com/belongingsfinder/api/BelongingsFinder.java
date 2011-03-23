package com.belongingsfinder.api;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import com.belongingsfinder.api.modules.DAOModule.PersistenceUnit;
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

	@Inject
	private Logger logger;

	/* Live environment */
	public BelongingsFinder() {
		this(Region.US_West, BucketName.LIVE, PersistenceUnit.LIVE);
		logger.log(Level.INFO, "LIVE environment settings enabled");
	}

	public BelongingsFinder(Region region, BucketName bucket, PersistenceUnit persistence) {
		inject(region, bucket, persistence);
	}

	/* Local development only */
	public static void main(String[] args) throws Exception {
		Component c = new Component();
		c.getServers().add(Protocol.HTTP, 8182);
		c.getDefaultHost().attach(new BelongingsFinder(Region.US_West, BucketName.DEV, PersistenceUnit.DEV));
		c.start();
	}

	@Override
	public Restlet createInboundRoot() {
		Router apiv1 = routerProvider.get();

		/*
		 * GET retrieve all belongings
		 * POST create new belongings
		 * 
		 * belongings
		 */
		apiv1.attach("/belongings", BelongingModelsServerResource.class);

		/*
		 * GET retrieve count of belongings :TYPE
		 * 
		 * belongings/count/:TYPE
		 */
		TemplateRoute belongingsCount = apiv1.attach("/belongings/count/{type}",
				typeFilterFactory.createFilter(finderFactory.createFinder(BelongingModelCountServerResource.class)));
		belongingsCount.getTemplate().getVariables().put("type", new Variable(Variable.TYPE_ALPHA));

		/*
		 * GET retrieve random belongings :N
		 * 
		 * belongings/random/:N
		 */

		TemplateRoute randomBelongings = apiv1.attach("/belongings/random/{number}",
				RandomBelongingModelServerResource.class);
		randomBelongings.getTemplate().getVariables().put("number", new Variable(Variable.TYPE_DIGIT));

		/*
		 * GET retrieve belonging :ID
		 * DELETE delete belonging :ID
		 * PUT update belonging :ID
		 * 
		 * belongings/id/:ID
		 */
		apiv1.attach("/belongings/id/{id}",
				uuidFilterFactory.createFilter(finderFactory.createFinder(BelongingModelServerResource.class)));

		/*
		 * GET retrieve a page of belongings :TYPE :NUMBER :OFFSET
		 * 
		 * belongings/:TYPE/:NUMBER/:OFFSET
		 */

		TemplateRoute belongingsPager = apiv1.attach("/belongings/{type}/{number}/{offset}",
				typeFilterFactory.createFilter(finderFactory.createFinder(PagingBelongingModelServerResource.class)));
		belongingsPager.getTemplate().getVariables().put("number", new Variable(Variable.TYPE_DIGIT));
		belongingsPager.getTemplate().getVariables().put("offset", new Variable(Variable.TYPE_DIGIT));
		belongingsPager.getTemplate().getVariables().put("type", new Variable(Variable.TYPE_ALPHA));

		/*
		 * POST execute a search on belongings
		 * 
		 * belongings/search
		 */

		apiv1.attach("/belongings/search", BelongingSearchServerResource.class);

		/*
		 * POST create a new category
		 * GET retrieve root categories
		 * 
		 * categories
		 */

		apiv1.attach("/categories", CategoryModelsServerResource.class);

		/*
		 * GET retrieve category :ID
		 * DELETE delete category :ID
		 * PUT update category :ID
		 * 
		 * categories/id/:ID
		 */

		apiv1.attach("/categories/id/{id}",
				uuidFilterFactory.createFilter(finderFactory.createFinder(CategoryModelServerResource.class)));

		/*
		 * GET retrieve the children of category :ID
		 * 
		 * categories/children/:ID
		 */

		apiv1.attach("/categories/children/{id}",
				uuidFilterFactory.createFilter(finderFactory.createFinder(ChildrenCategoryModelServerResource.class)));

		/*
		 * POST form submission for new belongings
		 * 
		 * mobile
		 */

		apiv1.attach("/mobile", MobileBelongingModelServerResource.class);

		Router app = routerProvider.get();

		/*
		 * attach api to version 1
		 */

		app.attach(v1, apiv1, Template.MODE_STARTS_WITH);

		// app.attach("/stuff", StuffServerResource.class);

		return app;
	}

	@Override
	public synchronized void start() throws Exception {
		persistService.start();
		indexer.index();
		registerServices(services);
		super.start();
		logger.log(Level.INFO, "BelongingsFinder started");
	}

	@Override
	public synchronized void stop() throws Exception {
		persistService.stop();
		super.stop();
		logger.log(Level.INFO, "BelongingsFinder stopped");
	}

	private void inject(Region region, BucketName bucket, PersistenceUnit persistence) {
		Injector injector = Guice.createInjector(new BelongingsFinderModules(persistence, bucket, region));
		injector.injectMembers(this);
	}

	private void registerServices(Set<Service> services) {
		for (Service service : services) {
			super.getServices().add(service);
		}
	}

}
