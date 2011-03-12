package com.belongingsfinder.api.event;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.belongingsfinder.api.annotations.Unwrapped;
import com.belongingsfinder.api.event.events.ModelCreatedEvent;
import com.belongingsfinder.api.event.events.ModelDeletedEvent;
import com.belongingsfinder.api.event.events.ModelEvent;
import com.belongingsfinder.api.event.events.ModelRetrievedEvent;
import com.belongingsfinder.api.event.events.ModelUpdatedEvent;
import com.belongingsfinder.api.model.Model;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Finbarr
 * 
 *         A proxy that sits atop an EventBus and permits the registration of ModelEventHandlers for ModelEvents that
 *         relate to specific Model types
 * 
 */
@Singleton
public class ModelEventProxy implements EventBus {

	private final Map<Class<? extends ModelEvent<?>>, Map<Class<? extends Model<?>>, Set<EventHandler<?>>>> proxy;

	private final Logger logger;
	private final EventBus eventBus;

	@Inject
	public ModelEventProxy(@Unwrapped EventBus eventBus, Logger logger) {
		proxy = new ConcurrentHashMap<Class<? extends ModelEvent<?>>, Map<Class<? extends Model<?>>, Set<EventHandler<?>>>>();
		this.logger = logger;
		this.eventBus = eventBus;
		eventBus.registerHandler(createProxyHandler(ModelCreatedEvent.class));
		eventBus.registerHandler(createProxyHandler(ModelDeletedEvent.class));
		eventBus.registerHandler(createProxyHandler(ModelRetrievedEvent.class));
		eventBus.registerHandler(createProxyHandler(ModelUpdatedEvent.class));
	}

	public <E extends Event> void fireEvent(E event) {
		eventBus.fireEvent(event);
	}

	@SuppressWarnings("unchecked")
	public void registerHandler(EventHandler<? extends Event> eventHandler) {
		if (!(eventHandler instanceof ModelEventHandler)) {
			eventBus.registerHandler(eventHandler);
		} else {
			ModelEventHandler<? extends Model<?>, ? extends ModelEvent<?>> handler = (ModelEventHandler<? extends Model<?>, ? extends ModelEvent<?>>) eventHandler;
			if (!proxy.containsKey(handler.getEventClass())) {
				if (logger != null) {
					logger.log(Level.WARNING, "ModelEvent class not supported: "
							+ handler.getEventClass().getSimpleName());
				}
				return;
			}
			if (!proxy.get(handler.getEventClass()).containsKey(handler.getModelClass())) {
				proxy.get(handler.getEventClass()).put(handler.getModelClass(),
						Collections.newSetFromMap(new ConcurrentHashMap<EventHandler<?>, Boolean>()));
			}
			proxy.get(handler.getEventClass()).get(handler.getModelClass()).add(handler);
		}
	}

	@SuppressWarnings("unchecked")
	public void unregisterHandler(EventHandler<? extends Event> eventHandler) {
		if (!(eventHandler instanceof ModelEventHandler)) {
			eventBus.registerHandler(eventHandler);
		} else {
			ModelEventHandler<? extends Model<?>, ? extends ModelEvent<?>> handler = (ModelEventHandler<? extends Model<?>, ? extends ModelEvent<?>>) eventHandler;
			if (!proxy.containsKey(handler.getEventClass())) {
				if (logger != null) {
					logger.log(Level.WARNING, "ModelEvent class not supported: "
							+ handler.getEventClass().getSimpleName());
				}
				return;
			}
			if (proxy.get(handler.getEventClass()).containsKey(handler.getModelClass())) {
				proxy.get(handler.getEventClass()).get(handler.getModelClass()).remove(handler);
				if (proxy.get(handler.getEventClass()).get(handler.getModelClass()).isEmpty()) {
					proxy.get(handler.getEventClass()).remove(handler.getModelClass());
				}
			}
		}
	}

	private <E extends ModelEvent<?>> ProxyHandler<E> createProxyHandler(Class<E> eventClass) {
		proxy.put(eventClass, new ConcurrentHashMap<Class<? extends Model<?>>, Set<EventHandler<?>>>());
		return new ProxyHandler<E>(eventClass);
	}

	private class ProxyHandler<E extends ModelEvent<?>> implements EventHandler<E> {
		private final Class<E> eventClass;

		@SuppressWarnings("unchecked")
		public ProxyHandler(Class<? extends ModelEvent<?>> eventClass) {
			this.eventClass = (Class<E>) eventClass;
		}

		public Class<? extends Event> getEventClass() {
			return eventClass;
		}

		@SuppressWarnings("unchecked")
		public void handle(E event) {
			if (proxy.containsKey(event.getClass()) && proxy.get(event.getClass()).containsKey(event.getModelClass())) {
				for (EventHandler<?> handler : proxy.get(event.getClass()).get(event.getModelClass())) {
					((EventHandler<E>) handler).handle(event);
				}
			}
			if (!proxy.containsKey(event.getClass())) {
				if (logger != null) {
					logger.log(Level.WARNING, "ModelEvent class not supported: " + event.getClass().getSimpleName());
				}
			}
		}
	}

}
