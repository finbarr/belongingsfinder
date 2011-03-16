package com.belongingsfinder.api.resource;

import java.util.List;

import javax.persistence.EntityManager;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.belongingsfinder.api.dao.ModelDAO;
import com.belongingsfinder.api.model.CategoryModel;
import com.belongingsfinder.api.search.CategoryModelSearch;
import com.google.inject.Inject;

public class CategoryModelsServerResource extends ServerResource {

	private final ThreadLocal<EntityManager> local;
	private final ModelDAO<CategoryModel> modelDAO;
	private final CategoryModelSearch search;

	@Inject
	public CategoryModelsServerResource(ThreadLocal<EntityManager> local, ModelDAO<CategoryModel> modelDAO,
			CategoryModelSearch search) {
		this.local = local;
		this.modelDAO = modelDAO;
		this.search = search;
	}

	@Post("json")
	public String createCategory(CategoryModel model) {
		if (search.categoryExists(model.getName())) {
			getResponse().setStatus(Status.CLIENT_ERROR_PRECONDITION_FAILED,
					"Category " + model.getName() + " already exists");
			return null;
		}
		return modelDAO.create(model);
	}

	@SuppressWarnings("unchecked")
	@Get("json")
	public List<CategoryModel> getRootCategories() {
		return local
				.get()
				.createQuery(
						"select category from CategoryModel as category where category.parent is null order by category.name asc")
				.getResultList();
	}

}
