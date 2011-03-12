package com.belongingsfinder.api.event;

import com.belongingsfinder.api.event.events.ModelEvent;
import com.belongingsfinder.api.model.Model;

/**
 * @author Finbarr
 * 
 * @param <T>
 * @param <E>
 */
public abstract class ModelEventHandler<T extends Model<T>, E extends ModelEvent<T>> implements EventHandler<E> {

	private final Class<T> modelClass;
	private final Class<E> eventClass;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ModelEventHandler(Class<? extends Model<?>> modelClass, Class<? extends ModelEvent> eventClass) {
		this.modelClass = (Class<T>) modelClass;
		this.eventClass = (Class<E>) eventClass;
	}

	public Class<? extends Event> getEventClass() {
		return eventClass;
	}

	public Class<? extends Model<T>> getModelClass() {
		return modelClass;
	}

	public abstract void handle(E event);

}
