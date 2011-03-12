package com.belongingsfinder.api.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.ejb.Ejb3Configuration;

import com.belongingsfinder.api.model.BelongingModel;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * @author Finbarr
 * 
 */
@Singleton
public class EntityManagerProvider implements Provider<EntityManager> {

	private volatile EntityManagerFactory entityManagerFactory;

	public EntityManager get() {
		if (entityManagerFactory == null) {
			synchronized (this) {
				if (entityManagerFactory == null) {
					entityManagerFactory = new Ejb3Configuration().addAnnotatedClass(BelongingModel.class)
							.configure("/hibernate.cfg.xml").buildEntityManagerFactory();
				}
			}
		}
		return entityManagerFactory.createEntityManager();
	}

}
