package com.belongingsfinder.api.resource;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.belongingsfinder.api.annotations.Transactional;
import com.belongingsfinder.api.model.BelongingModel;
import com.google.inject.Inject;

/**
 * @author finbarr
 * 
 */
public class RandomBelongingModelServerResource extends ServerResource {

	private final ThreadLocal<EntityManager> local;

	@Inject
	public RandomBelongingModelServerResource(ThreadLocal<EntityManager> local) {
		this.local = local;
	}

	@SuppressWarnings("unchecked")
	@Get("json")
	@Transactional
	public List<BelongingModel> getRandomBelongings() {
		final int number = Integer.parseInt(getRequest().getAttributes().get("number").toString());
		Query q = local.get().createQuery(
				"select b from BelongingModel as b where b.imageUrl is not null order by rand()");
		q.setMaxResults(number);
		return q.getResultList();
	}

}
