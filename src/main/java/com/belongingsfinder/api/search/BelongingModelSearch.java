package com.belongingsfinder.api.search;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.belongingsfinder.api.model.BelongingFilter;
import com.belongingsfinder.api.model.BelongingModel;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

/**
 * @author finbarr
 * 
 */
@Singleton
public class BelongingModelSearch {

	private final Provider<EntityManager> provider;

	@Inject
	public BelongingModelSearch(Provider<EntityManager> provider) {
		this.provider = provider;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public List<BelongingModel> search(BelongingFilter bf) {
		FullTextEntityManager ftem = Search.getFullTextEntityManager(provider.get());
		QueryBuilder qb = ftem.getSearchFactory().buildQueryBuilder().forEntity(BelongingModel.class).get();
		BooleanJunction<BooleanJunction> junc = qb.bool();
		junc.should(qb.keyword().onField("description").matching(bf.getTerms()).createQuery());
		if (bf.getType() != null) {
			junc.must(qb.keyword().onField("type").matching(bf.getType()).createQuery());
		}
		if (bf.getCategoryId() != null) {
			junc.must(qb.keyword().onField("category.id").matching(bf.getCategoryId()).createQuery());
		}
		// TODO location stuff
		// bounding box and range query
		FullTextQuery q = ftem.createFullTextQuery(junc.createQuery(), BelongingModel.class);
		if (bf.getMaxResults() > 0) {
			q.setMaxResults(bf.getMaxResults());
		}
		if (bf.getOffset() > 0) {
			q.setFirstResult(bf.getOffset());
		}
		return q.getResultList();
	}
}
