package com.belongingsfinder.api.resource;

import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.belongingsfinder.api.dao.ModelDAO;
import com.belongingsfinder.api.model.CategoryModel;
import com.google.inject.Inject;

public class CategoriesServerResource extends ServerResource {

	private final ModelDAO<CategoryModel> modelDAO;

	@Inject
	public CategoriesServerResource(ModelDAO<CategoryModel> modelDAO) {
		this.modelDAO = modelDAO;
	}

	@Get("json")
	public List<CategoryModel> getCategories() {
		return modelDAO.retrieveAll();
	}

}
