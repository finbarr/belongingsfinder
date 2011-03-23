package com.belongingsfinder.api.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.belongingsfinder.api.dao.ModelDAO;
import com.belongingsfinder.api.model.Model;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

/**
 * @author Finbarr
 * 
 * @param <T>
 */
public class JPAModelDAO<T extends Model<T>> implements ModelDAO<T> {

	private final Class<T> type;
	private final Provider<EntityManager> provider;

	@Inject
	public JPAModelDAO(Class<T> type, Provider<EntityManager> provider) {
		this.type = type;
		this.provider = provider;
	}

	@Transactional
	public String create(T model) {
		provider.get().persist(model);
		return model.getId();
	}

	@Transactional
	public boolean del(String id) {
		Query q = provider.get().createQuery(
				new StringBuilder("delete from ").append(type.getClass().getName()).append(" where id = :id limit 1")
						.toString());
		q.setParameter("id", id);
		return q.executeUpdate() == 1;
	}

	@Transactional
	public List<T> retrieve(int number) {
		TypedQuery<T> query = provider.get().createQuery(createRetrievalQuery());
		query.setMaxResults(number);
		return query.getResultList();
	}

	@Transactional
	public T retrieve(String id) {
		T model = provider.get().find(type, id);
		provider.get().detach(model);
		return model;
	}

	@Transactional
	public List<T> retrieveAll() {
		return provider.get().createQuery(createRetrievalQuery()).getResultList();
	}

	@Transactional
	public void update(T model) {
		provider.get().merge(model);
	}

	private CriteriaQuery<T> createRetrievalQuery() {
		EntityManager em = provider.get();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(type);
		Root<T> root = query.from(type);
		query.select(root);
		return query;
	}

}
