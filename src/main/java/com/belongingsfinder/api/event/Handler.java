package com.belongingsfinder.api.event;

/**
 * @author Finbarr
 * 
 * @param <T>
 */
public interface Handler<T> {

	public void handle(T t);

}
