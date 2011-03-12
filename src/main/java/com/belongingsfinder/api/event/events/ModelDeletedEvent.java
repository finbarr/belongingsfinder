package com.belongingsfinder.api.event.events;

import com.belongingsfinder.api.model.Model;

/**
 * @author Finbarr
 * 
 * @param <T>
 */
public class ModelDeletedEvent<T extends Model<T>> extends ModelEvent<T> {

	private final String id;

	public ModelDeletedEvent(Class<T> modelClass, String id) {
		super(modelClass);
		this.id = id;
	}

	public String getId() {
		return id;
	}

}
