package com.belongingsfinder.api.search;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.belongingsfinder.api.annotations.Transactional;
import com.belongingsfinder.api.model.BelongingFilter;
import com.belongingsfinder.api.model.BelongingModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class BelongingModelSearch {

	private final ThreadLocal<EntityManager> local;

	@Inject
	public BelongingModelSearch(ThreadLocal<EntityManager> local) {
		this.local = local;
	}

	@Transactional
	public List<BelongingModel> search(BelongingFilter bf) {
		FullTextEntityManager ftem = Search.getFullTextEntityManager(local.get());
		QueryBuilder qb = ftem.getSearchFactory().buildQueryBuilder().forEntity(BelongingModel.class).get();
		BooleanJunction<BooleanJunction> junc = qb.bool();
		junc.should(qb.keyword().onField("description").matching(bf.getTerms()).createQuery());
		if (bf.getType() != null) {
			junc.must(qb.keyword().onField("type").matching(bf.getType()).createQuery());
		}
		if (bf.getCategoryId() != null) {
			junc.must(qb.keyword().onField("categoryId").matching(bf.getCategoryId()).createQuery());
		}
		// TODO location stuff
		Query q = ftem.createFullTextQuery(junc.createQuery(), BelongingModel.class);
		if (bf.getMaxResults() > 0) {
			q.setMaxResults(bf.getMaxResults());
		}
		if (bf.getOffset() > 0) {
			q.setFirstResult(bf.getOffset());
		}
		return q.getResultList();
	}
}
