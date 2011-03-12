package com.belongingsfinder.api.event;

/**
 * @author finbarr
 * 
 * @param <E>
 */
public interface EventHandler<E extends Event> extends Handler<E> {

	public Class<? extends Event> getEventClass();

}
