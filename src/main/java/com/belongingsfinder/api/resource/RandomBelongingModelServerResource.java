package com.belongingsfinder.api.resource;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.belongingsfinder.api.model.BelongingModel;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

/**
 * @author finbarr
 * 
 */
public class RandomBelongingModelServerResource extends ServerResource {

	private final Provider<EntityManager> provider;

	@Inject
	public RandomBelongingModelServerResource(Provider<EntityManager> provider) {
		this.provider = provider;
	}

	@SuppressWarnings("unchecked")
	@Get("json")
	@Transactional
	public List<BelongingModel> getRandomBelongings() {
		final int number = Integer.parseInt(getRequest().getAttributes().get("number").toString());
		Query q = provider.get().createQuery(
				"select b from BelongingModel as b where b.images is not null order by rand()");
		q.setMaxResults(number);
		return q.getResultList();
	}

}
