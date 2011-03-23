package com.belongingsfinder.api.search;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.model.BelongingSearchRequest;
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
	public List<BelongingModel> search(BelongingSearchRequest request) {
		FullTextEntityManager ftem = Search.getFullTextEntityManager(provider.get());
		QueryBuilder qb = ftem.getSearchFactory().buildQueryBuilder().forEntity(BelongingModel.class)
				.overridesForField("description_en", "en").overridesForField("description_jp", "jp").get();
		BooleanJunction<BooleanJunction> junc = qb.bool();
		junc.should(qb.keyword().onField("description_" + request.getLanguage().toString())
				.matching(request.getTerms()).createQuery());
		if (request.getType() != null) {
			junc.must(qb.keyword().onField("type").matching(request.getType()).createQuery());
		}
		if (request.getCategoryId() != null) {
			junc.must(qb.keyword().onField("category.id").matching(request.getCategoryId()).createQuery());
		}
		// TODO location stuff
		// bounding box and range query
		FullTextQuery q = ftem.createFullTextQuery(junc.createQuery(), BelongingModel.class);
		if (request.getMaxResults() > 0) {
			q.setMaxResults(request.getMaxResults());
		}
		if (request.getOffset() > 0) {
			q.setFirstResult(request.getOffset());
		}
		return q.getResultList();
	}

}
