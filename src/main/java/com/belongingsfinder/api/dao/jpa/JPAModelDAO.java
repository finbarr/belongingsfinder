package com.belongingsfinder.api.dao.jpa;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;

import com.belongingsfinder.api.annotations.Transactional;
import com.belongingsfinder.api.dao.ModelDAO;
import com.belongingsfinder.api.model.Model;
import com.google.inject.Inject;

/**
 * @author Finbarr
 * 
 * @param <T>
 */
public class JPAModelDAO<T extends Model<T>> implements ModelDAO<T> {

	private final Class<T> type;
	private final ThreadLocal<EntityManager> local;

	@Inject
	public JPAModelDAO(Class<T> type, ThreadLocal<EntityManager> local) {
		this.type = type;
		this.local = local;
	}

	@Transactional
	public String create(T model) {
		// give new models a random id
		model.setId(UUID.randomUUID().toString());
		// obtain entitymanager and persist model
		local.get().persist(model);
		// return the id
		return model.getId();
	}

	@Transactional
	public boolean del(String id) {
		// if model exists
		T model = retrieveInternal(id);
		if (model != null) {
			// obtain entitymanager and remove
			local.get().remove(model);
			return true;
		}
		return false;
	}

	@Transactional
	public T retrieve(String id) {
		// retrieve model
		T model = retrieveInternal(id);
		// detach from entitymanager
		local.get().detach(model);
		return model;
	}

	@Transactional
	public List<T> retrieveAll() {
		EntityManager em = local.get();
		return em.createQuery(em.getCriteriaBuilder().createQuery(type)).getResultList();
	}

	@Transactional
	public void update(T model) {
		local.get().merge(model);
	}

	private T retrieveInternal(String id) {
		// obtain entitymanager and attempt to find entity of type with id
		return local.get().find(type, id);
	}
}