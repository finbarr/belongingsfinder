package com.belongingsfinder.api.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.restlet.service.Service;

import com.belongingsfinder.api.annotations.Unwrapped;
import com.belongingsfinder.api.event.Event;
import com.belongingsfinder.api.event.EventBus;
import com.belongingsfinder.api.event.EventHandler;
import com.belongingsfinder.api.event.events.ModelCreatedEvent;
import com.belongingsfinder.api.event.events.ModelDeletedEvent;
import com.belongingsfinder.api.event.events.ModelRetrievedEvent;
import com.belongingsfinder.api.event.events.ModelUpdatedEvent;
import com.google.inject.Inject;

public class CRUDLoggingService extends Service {

	private final Logger logger;
	private final EventBus eventBus;
	private final EventHandler<ModelCreatedEvent<?>> created;
	private final EventHandler<ModelRetrievedEvent<?>> retrieved;
	private final EventHandler<ModelUpdatedEvent<?>> updated;
	private final EventHandler<ModelDeletedEvent<?>> deleted;

	@Inject
	public CRUDLoggingService(final Logger logger, @Unwrapped EventBus eventBus) {
		this.logger = logger;
		this.eventBus = eventBus;
		created = new EventHandler<ModelCreatedEvent<?>>() {
			@Override
			public Class<? extends Event> getEventClass() {
				return ModelCreatedEvent.class;
			}

			@Override
			public void handle(ModelCreatedEvent<?> t) {
				logger.log(
						Level.INFO,
						new StringBuilder(t.getModelClass().getSimpleName()).append(" created ")
								.append(t.getModel().getId()).toString());
			}
		};
		retrieved = new EventHandler<ModelRetrievedEvent<?>>() {
			@Override
			public Class<? extends Event> getEventClass() {
				return ModelRetrievedEvent.class;
			}

			@Override
			public void handle(ModelRetrievedEvent<?> t) {
				logger.log(Level.INFO, new StringBuilder(t.getModelClass().getSimpleName()).append(" retrieved ")
						.append(t.getModel().getId()).toString());
			}
		};
		updated = new EventHandler<ModelUpdatedEvent<?>>() {
			@Override
			public Class<? extends Event> getEventClass() {
				return ModelUpdatedEvent.class;
			}

			@Override
			public void handle(ModelUpdatedEvent<?> t) {
				logger.log(
						Level.INFO,
						new StringBuilder(t.getModelClass().getSimpleName()).append(" updated ")
								.append(t.getModel().getId()).toString());
			}
		};
		deleted = new EventHandler<ModelDeletedEvent<?>>() {
			@Override
			public Class<? extends Event> getEventClass() {
				return ModelDeletedEvent.class;
			}

			@Override
			public void handle(ModelDeletedEvent<?> t) {
				logger.log(Level.INFO,
						new StringBuilder(t.getModelClass().toString()).append(" deleted ").append(t.getId())
								.toString());
			}
		};
	}

	@Override
	public synchronized void start() throws Exception {
		eventBus.registerHandler(created);
		eventBus.registerHandler(retrieved);
		eventBus.registerHandler(updated);
		eventBus.registerHandler(deleted);
		super.start();
		logger.log(Level.INFO, "Logging CRUD events");
	}

	@Override
	public synchronized void stop() throws Exception {
		eventBus.unregisterHandler(created);
		eventBus.unregisterHandler(deleted);
		eventBus.unregisterHandler(retrieved);
		eventBus.unregisterHandler(updated);
		super.stop();
		logger.log(Level.INFO, "Not Logging CRUD events");
	}

}
