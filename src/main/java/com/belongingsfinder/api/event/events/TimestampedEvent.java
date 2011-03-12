package com.belongingsfinder.api.event.events;

import com.belongingsfinder.api.event.Event;

/**
 * @author Finbarr
 * 
 */
public class TimestampedEvent implements Event {

	private final long time;

	public TimestampedEvent() {
		time = System.currentTimeMillis();
	}

	public int compareTo(Event o) {
		if (o.getTime() == time) {
			return 0;
		} else if (o.getTime() > time) {
			return -1;
		} else {
			return 1;
		}
	}

	public long getTime() {
		return time;
	}

}
