package com.belongingsfinder.api.model;

/**
 * @author Finbarr
 * 
 * @param <T>
 */
public interface Model<T extends Model<T>> {

	public String getId();

	public void setId(String id);

}
