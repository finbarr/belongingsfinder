package com.belongingsfinder.api.search;

import java.util.concurrent.Semaphore;

import javax.persistence.EntityManager;

import org.hibernate.search.jpa.Search;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * @author finbarr
 * 
 */
@Singleton
public class SearchIndexer {

	private final Semaphore lock = new Semaphore(1);
	private final Provider<EntityManager> provider;

	@Inject
	public SearchIndexer(Provider<EntityManager> provider) {
		this.provider = provider;
	}

	public void index() {
		try {
			if (lock.tryAcquire(1)) {
				Search.getFullTextEntityManager(provider.get()).createIndexer().startAndWait();
				// deliberately do not release the lock
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
