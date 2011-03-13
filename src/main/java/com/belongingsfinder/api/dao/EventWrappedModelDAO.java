package com.belongingsfinder.api.dao;

import java.util.List;

import com.belongingsfinder.api.annotations.Unwrapped;
import com.belongingsfinder.api.event.EventBus;
import com.belongingsfinder.api.event.events.ModelCreatedEvent;
import com.belongingsfinder.api.event.events.ModelDeletedEvent;
import com.belongingsfinder.api.event.events.ModelRetrievedEvent;
import com.belongingsfinder.api.event.events.ModelUpdatedEvent;
import com.belongingsfinder.api.model.Model;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author finbarr
 * 
 * @param <T>
 *            Model of type T
 */
@Singleton
public class EventWrappedModelDAO<T extends Model<T>> implements ModelDAO<T> {

	private final EventBus eventBus;
	private final ModelDAO<T> modelService;
	private final Class<T> type;

	@Inject
	public EventWrappedModelDAO(EventBus eventBus, @Unwrapped ModelDAO<T> modelService, Class<T> type) {
		this.eventBus = eventBus;
		this.modelService = modelService;
		this.type = type;
	}

	public String create(T model) {
		String id = modelService.create(model);
		model.setId(id);
		eventBus.fireEvent(new ModelCreatedEvent<T>(type, model));
		return id;
	}

	public boolean del(String id) {
		boolean deleted = modelService.del(id);
		if (deleted) {
			eventBus.fireEvent(new ModelDeletedEvent<T>(type, id));
		}
		return deleted;
	}

	// not an interesting event
	public List<T> retrieve(int number) {
		return modelService.retrieve(number);
	}

	public T retrieve(String id) {
		T model = modelService.retrieve(id);
		eventBus.fireEvent(new ModelRetrievedEvent<T>(type, model));
		return model;
	}

	// not an interesting event
	public List<T> retrieveAll() {
		return modelService.retrieveAll();
	}

	public void update(T model) {
		modelService.update(model);
		eventBus.fireEvent(new ModelUpdatedEvent<T>(type, model));
	}

}
