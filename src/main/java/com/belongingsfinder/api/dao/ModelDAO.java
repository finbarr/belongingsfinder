package com.belongingsfinder.api.dao;

import java.util.List;

import com.belongingsfinder.api.model.Model;

/**
 * @author finbarr
 * 
 * @param <T>
 *            The model Type T
 */
public interface ModelDAO<T extends Model<T>> {

	/**
	 * Create a model of type T
	 * 
	 * @param model
	 *            the model to be created
	 * @return the id of the model
	 */
	public String create(T model);

	/**
	 * Delete the model corresponding to the id
	 * 
	 * @param id
	 *            the id of the model to be deleted
	 * @return whether or not a model was found and deleted
	 */
	public boolean del(String id);

	/**
	 * Retrieve a model of type T based on the id
	 * 
	 * @param id
	 *            the id of the model
	 * @return the model
	 */
	public T retrieve(String id);

	/**
	 * Retrieve all models of type T
	 * 
	 * @return models of type T
	 */
	public List<T> retrieveAll();

	/**
	 * Update a model of type T
	 * 
	 * @param model
	 *            the model to be updated
	 */
	public void update(T model);

}
