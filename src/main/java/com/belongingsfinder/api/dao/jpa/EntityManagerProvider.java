package com.belongingsfinder.api.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.ejb.Ejb3Configuration;

import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.model.CategoryModel;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * @author Finbarr
 * 
 */
@Singleton
public class EntityManagerProvider implements Provider<EntityManager> {

	private final EntityManagerFactory entityManagerFactory;

	public EntityManagerProvider() {
		entityManagerFactory = new Ejb3Configuration().addAnnotatedClass(BelongingModel.class)
				.addAnnotatedClass(CategoryModel.class).configure("/hibernate.cfg.xml").buildEntityManagerFactory();
	}

	public EntityManager get() {
		return entityManagerFactory.createEntityManager();
	}

}
