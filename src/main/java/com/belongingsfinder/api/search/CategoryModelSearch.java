package com.belongingsfinder.api.search;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CategoryModelSearch {

	private final ThreadLocal<EntityManager> local;

	@Inject
	public CategoryModelSearch(ThreadLocal<EntityManager> local) {
		this.local = local;
	}

	public boolean categoryExists(String name) {
		Query query = local.get()
				.createQuery("select count(category) from CategoryModel as category where category.name = :name")
				.setParameter("name", name);
		return (Long) query.getSingleResult() > 0L;
	}

}
