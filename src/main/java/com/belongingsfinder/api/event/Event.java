package com.belongingsfinder.api.event;

/**
 * @author finbarr
 * 
 */
public interface Event extends Comparable<Event> {

	public long getTime();

}
