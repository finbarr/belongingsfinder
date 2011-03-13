package com.belongingsfinder.api.util;

import java.util.concurrent.Semaphore;

import javax.persistence.EntityManager;

import org.hibernate.search.jpa.Search;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SearchIndexer {

	private final Semaphore lock = new Semaphore(1);
	private final ThreadLocal<EntityManager> local;

	@Inject
	public SearchIndexer(ThreadLocal<EntityManager> local) {
		this.local = local;
	}

	public void index() {
		try {
			if (lock.tryAcquire(1)) {
				Search.getFullTextEntityManager(local.get()).createIndexer().startAndWait();
				// deliberately do not release the lock
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
