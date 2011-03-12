package com.belongingsfinder.api.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.restlet.service.Service;

import com.belongingsfinder.api.event.Event;
import com.belongingsfinder.api.event.EventBus;
import com.belongingsfinder.api.event.EventHandler;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author finbarr
 * 
 */
@Singleton
public class AsyncEventBusService extends Service implements EventBus {

	private final Map<Class<? extends Event>, Set<EventHandler<? extends Event>>> handlers;

	private final BlockingQueue<Runnable> queue;

	private final Thread asyncEventBusThread;

	private final ExecutorService executorService;

	private final Logger logger;

	@Inject
	public AsyncEventBusService(ExecutorService executorService, Logger logger) {
		super();
		handlers = new ConcurrentHashMap<Class<? extends Event>, Set<EventHandler<? extends Event>>>();
		queue = new LinkedBlockingQueue<Runnable>();
		asyncEventBusThread = new Thread(new AsyncEventBusRunnable());
		this.executorService = executorService;
		this.logger = logger;
	}

	@SuppressWarnings("unchecked")
	public <E extends Event> void fireEvent(final E event) {
		if (logger != null) {
			logger.log(Level.INFO, event.getClass().getSimpleName() + " : " + event.getTime());
		}
		if (handlers.containsKey(event.getClass())) {
			final List<EventHandler<E>> h = new ArrayList<EventHandler<E>>();
			for (final EventHandler<? extends Event> handler : handlers.get(event.getClass())) {
				h.add((EventHandler<E>) handler);
			}
			queue.add(new HandlerRunnable<E>(h, event));
		}
	}

	public void registerHandler(EventHandler<? extends Event> handler) {
		Class<? extends Event> eventType = handler.getEventClass();
		if (!handlers.containsKey(eventType)) {
			handlers.put(eventType,
					Collections.newSetFromMap(new ConcurrentHashMap<EventHandler<? extends Event>, Boolean>()));
		}
		handlers.get(eventType).add(handler);
	}

	@Override
	public synchronized void start() throws Exception {
		asyncEventBusThread.start();
		super.start();
	}

	@Override
	public synchronized void stop() throws Exception {
		asyncEventBusThread.interrupt();
		asyncEventBusThread.join();
		super.stop();
	}

	public void unregisterHandler(EventHandler<? extends Event> handler) {
		Class<? extends Event> eventType = handler.getEventClass();
		if (handlers.containsKey(eventType)) {
			handlers.get(eventType).remove(handler);
			if (handlers.get(eventType).isEmpty()) {
				handlers.remove(eventType);
			}
		}
	}

	private class AsyncEventBusRunnable implements Runnable {
		public void run() {
			while (true) {
				try {
					executorService.submit(queue.take());
					if (Thread.currentThread().isInterrupted()) {
						break;
					}
				} catch (InterruptedException e) {
					break;
				}
			}
		}
	}

	private static class HandlerRunnable<E extends Event> implements Runnable {
		private final List<EventHandler<E>> h;
		private final E e;

		public HandlerRunnable(final List<EventHandler<E>> h, E e) {
			this.h = h;
			this.e = e;
		}

		public void run() {
			for (EventHandler<E> handler : h) {
				handler.handle(e);
			}
		}
	}

}
