package com.belongingsfinder.api.event.events;

import com.belongingsfinder.api.model.Model;

/**
 * @author Finbarr
 * 
 * @param <T>
 */
public abstract class ModelEvent<T extends Model<T>> extends TimestampedEvent {

	private final Class<T> modelClass;

	public ModelEvent(Class<T> modelClass) {
		this.modelClass = modelClass;
	}

	public Class<T> getModelClass() {
		return modelClass;
	}

}
