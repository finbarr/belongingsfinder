package com.belongingsfinder.api.modules;

import org.restlet.service.Service;

import com.belongingsfinder.api.annotations.Unwrapped;
import com.belongingsfinder.api.event.EventBus;
import com.belongingsfinder.api.event.ModelEventProxy;
import com.belongingsfinder.api.service.AsyncEventBusService;
import com.belongingsfinder.api.service.CRUDLoggingService;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

/**
 * @author finbarr
 * 
 */
public class ServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(EventBus.class).annotatedWith(Unwrapped.class).to(AsyncEventBusService.class);
		bind(EventBus.class).to(ModelEventProxy.class);
		Multibinder<Service> serviceBinder = Multibinder.newSetBinder(binder(), Service.class);
		serviceBinder.addBinding().to(AsyncEventBusService.class);
		serviceBinder.addBinding().to(CRUDLoggingService.class);
		// serviceBinder.addBinding().to(BelongingModelMatchingService.class);
	}

}
