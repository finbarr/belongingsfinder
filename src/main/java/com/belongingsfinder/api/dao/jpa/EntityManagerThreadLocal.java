package com.belongingsfinder.api.dao.jpa;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * @author finbarr
 * 
 */
@Singleton
public class EntityManagerThreadLocal extends ThreadLocal<EntityManager> {

	private final Provider<EntityManager> entityManagerProvider;

	@Inject
	public EntityManagerThreadLocal(Provider<EntityManager> entityManagerProvider) {
		this.entityManagerProvider = entityManagerProvider;
	}

	@Override
	protected EntityManager initialValue() {
		return entityManagerProvider.get();
	}

}
