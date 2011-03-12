package com.belongingsfinder.api.service;

import org.restlet.service.Service;

import com.belongingsfinder.api.dao.ModelDAO;
import com.belongingsfinder.api.event.EventBus;
import com.belongingsfinder.api.event.ModelEventHandler;
import com.belongingsfinder.api.event.events.ModelCreatedEvent;
import com.belongingsfinder.api.event.events.ModelUpdatedEvent;
import com.belongingsfinder.api.model.BelongingModel;
import com.google.inject.Inject;

public class BelongingModelMatchingService extends Service {

	private final EventBus eventBus;
	private final ModelDAO<BelongingModel> modelDAO;
	private final ModelEventHandler<BelongingModel, ModelCreatedEvent<BelongingModel>> created;
	private final ModelEventHandler<BelongingModel, ModelUpdatedEvent<BelongingModel>> updated;

	@Inject
	public BelongingModelMatchingService(EventBus eventBus, ModelDAO<BelongingModel> modelDAO) {
		this.eventBus = eventBus;
		this.modelDAO = modelDAO;
		ModelEventHandler<BelongingModel, ModelCreatedEvent<BelongingModel>> created = new ModelEventHandler<BelongingModel, ModelCreatedEvent<BelongingModel>>(
				BelongingModel.class, ModelCreatedEvent.class) {
			@Override
			public void handle(ModelCreatedEvent<BelongingModel> b) {

			}
		};

		this.created = created;

		ModelEventHandler<BelongingModel, ModelUpdatedEvent<BelongingModel>> updated = new ModelEventHandler<BelongingModel, ModelUpdatedEvent<BelongingModel>>(
				BelongingModel.class, ModelUpdatedEvent.class) {
			@Override
			public void handle(ModelUpdatedEvent<BelongingModel> b) {

			}
		};

		this.updated = updated;

	}

}
