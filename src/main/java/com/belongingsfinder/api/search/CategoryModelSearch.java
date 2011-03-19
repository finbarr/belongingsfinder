package com.belongingsfinder.api.search;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

/**
 * @author finbarr
 * 
 */
@Singleton
public class CategoryModelSearch {

	private final Provider<EntityManager> provider;

	@Inject
	public CategoryModelSearch(Provider<EntityManager> provider) {
		this.provider = provider;
	}

	@Transactional
	public boolean categoryExists(String name) {
		Query query = provider.get()
				.createQuery("select count(category) from CategoryModel as category where category.name = :name")
				.setParameter("name", name);
		return (Long) query.getSingleResult() > 0L;
	}

}
