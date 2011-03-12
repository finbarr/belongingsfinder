package com.belongingsfinder.api.event;

/**
 * @author finbarr
 * 
 */
public interface EventBus {

	public <E extends Event> void fireEvent(E event);

	public void registerHandler(EventHandler<? extends Event> handler);

	public void unregisterHandler(EventHandler<? extends Event> handler);

}
