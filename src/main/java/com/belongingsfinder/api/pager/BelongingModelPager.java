package com.belongingsfinder.api.pager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.belongingsfinder.api.annotations.Transactional;
import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.model.BelongingModel.Type;
import com.google.inject.Inject;
import com.google.inject.Singleton;

// This class could easily be generic

/**
 * @author finbarr
 * 
 */
@Singleton
public class BelongingModelPager implements ModelPager<BelongingModel> {

	private final Type type;
	private final ThreadLocal<EntityManager> local;

	@Inject
	public BelongingModelPager(Type type, ThreadLocal<EntityManager> local) {
		this.type = type;
		this.local = local;
	}

	@Transactional
	public long count() {
		String query = "select count(b) from BelongingModel b";
		if (!type.equals(BelongingModel.Type.ALL)) {
			query += " where b.type = :type";
		}
		Query q = local.get().createQuery(query);
		if (!type.equals(BelongingModel.Type.ALL)) {
			q.setParameter("type", type);
		}
		return (Long) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<BelongingModel> retrieve(int number, int offset) {
		Query q = local.get().createQuery(
				"select b from BelongingModel b where b.type = :type order by b.lastUpdated desc");
		q.setParameter("type", type);
		q.setMaxResults(number);
		q.setFirstResult(offset);
		return q.getResultList();
	}

}
