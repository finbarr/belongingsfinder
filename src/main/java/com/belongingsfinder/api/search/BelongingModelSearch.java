package com.belongingsfinder.api.search;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

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

	public List<BelongingModel> search(BelongingFilter bf) {
		FullTextEntityManager ftem = Search.getFullTextEntityManager(local.get());
		QueryBuilder qb = ftem.getSearchFactory().buildQueryBuilder().forEntity(BelongingModel.class).get();
		org.apache.lucene.search.Query query = qb.keyword().onField("description").matching(bf.getTerms())
				.createQuery();

		return null;
	}

}
