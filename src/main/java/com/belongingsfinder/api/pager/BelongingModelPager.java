package com.belongingsfinder.api.pager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.model.BelongingModel.BelongingType;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

// This class could easily be generic

/**
 * @author finbarr
 * 
 */
@Singleton
public class BelongingModelPager implements ModelPager<BelongingModel> {

	private final BelongingType type;
	private final Provider<EntityManager> provider;

	@Inject
	public BelongingModelPager(BelongingType type, Provider<EntityManager> provider) {
		this.type = type;
		this.provider = provider;
	}

	@Transactional
	public long count() {
		String query = "select count(b) from BelongingModel b";
		if (!type.equals(BelongingModel.BelongingType.ALL)) {
			query += " where b.type = :type";
		}
		Query q = provider.get().createQuery(query);
		if (!type.equals(BelongingModel.BelongingType.ALL)) {
			q.setParameter("type", type);
		}
		return (Long) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<BelongingModel> retrieve(int number, int offset) {
		Query q = provider.get().createQuery(
				"select b from BelongingModel b where b.type = :type order by b.lastUpdated desc");
		q.setParameter("type", type);
		q.setMaxResults(number);
		q.setFirstResult(offset);
		return q.getResultList();
	}

}
