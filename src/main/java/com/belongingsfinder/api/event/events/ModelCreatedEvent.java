package com.belongingsfinder.api.event.events;

import com.belongingsfinder.api.model.Model;

/**
 * @author Finbarr
 * 
 * @param <T>
 */
public class ModelCreatedEvent<T extends Model<T>> extends ModelEvent<T> {

	private final T model;

	public ModelCreatedEvent(Class<T> modelClass, T model) {
		super(modelClass);
		this.model = model;
	}

	public T getModel() {
		return model;
	}

}
