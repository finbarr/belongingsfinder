package com.belongingsfinder.api.resource;

import java.util.List;

import javax.persistence.EntityManager;

import org.restlet.resource.Get;

import com.belongingsfinder.api.model.CategoryModel;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

/**
 * @author finbarr
 * 
 */
public class CategoryModelsServerResource extends ValidatedServerResource {

	private final Provider<EntityManager> provider;

	// private final ModelDAO<CategoryModel> modelDAO;
	// private final CategoryModelSearch search;

	@Inject
	public CategoryModelsServerResource(Provider<EntityManager> provider/*
																		 * , ModelDAO<CategoryModel> modelDAO,
																		 * CategoryModelSearch search
																		 */) {
		this.provider = provider;
		// this.modelDAO = modelDAO;
		// this.search = search;
	}

	/*
	 * @Post("json")
	 * public String createCategory(CategoryModel model) {
	 * if (search.categoryExists(model.getName())) {
	 * getResponse().setStatus(Status.CLIENT_ERROR_PRECONDITION_FAILED,
	 * new StringBuilder("Category ").append(model.getName()).append(" already exists").toString());
	 * return null;
	 * }
	 * return modelDAO.create(model);
	 * }
	 */

	@SuppressWarnings("unchecked")
	@Get("json")
	@Transactional
	public List<CategoryModel> getRootCategories() {
		return provider
				.get()
				.createQuery(
						"select category from CategoryModel as category where category.parent is null order by category.name asc")
				.setHint("org.hibernate.cacheable", true).getResultList();
	}

}
