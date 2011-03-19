package com.belongingsfinder.api.resource;

import java.util.List;

import javax.persistence.EntityManager;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.belongingsfinder.api.model.CategoryModel;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author finbarr
 * 
 */
public class ChildrenCategoryModelServerResource extends ServerResource {

	private final Provider<EntityManager> provider;

	@Inject
	public ChildrenCategoryModelServerResource(Provider<EntityManager> provider) {
		this.provider = provider;
	}

	@SuppressWarnings("unchecked")
	@Get("json")
	public List<CategoryModel> getChildren() {
		return provider
				.get()
				.createQuery(
						"select category from CategoryModel as category where category.parent.id = :id order by category.name asc")
				.setParameter("id", getId()).getResultList();
	}

	private String getId() {
		return getRequest().getAttributes().get("id").toString();
	}

}
