package com.belongingsfinder.api.resource;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.belongingsfinder.api.annotations.Transactional;
import com.belongingsfinder.api.model.BelongingFilter;
import com.belongingsfinder.api.model.BelongingModel;
import com.google.inject.Inject;

public class BelongingSearchServerResource extends ServerResource {

	private final ThreadLocal<EntityManager> local;

	@Inject
	public BelongingSearchServerResource(ThreadLocal<EntityManager> local) {
		this.local = local;
	}

	@Post("json")
	@Transactional
	public List<BelongingModel> findBelongings(BelongingFilter filter) {
		CriteriaBuilder builder = local.get().getCriteriaBuilder();
		CriteriaQuery<BelongingModel> criteria = builder.createQuery(BelongingModel.class);
		Root<BelongingModel> root = criteria.from(BelongingModel.class);
		criteria.select(root);
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getCategory() != null) {

		}

	}

}
