package com.belongingsfinder.api.resource;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.belongingsfinder.api.dao.ModelDAO;
import com.belongingsfinder.api.model.CategoryModel;
import com.google.inject.Inject;

public class CategoryModelsServerResource extends ServerResource {

	private final ModelDAO<CategoryModel> modelDAO;

	@Inject
	public CategoryModelsServerResource(ModelDAO<CategoryModel> modelDAO) {
		this.modelDAO = modelDAO;
	}

	@Post("json")
	public String createCategory(CategoryModel model) {
		// TODO search to check Category doesn't already exist
		return modelDAO.create(model);
	}

}
