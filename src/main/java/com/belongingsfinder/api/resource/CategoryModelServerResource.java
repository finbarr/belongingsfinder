package com.belongingsfinder.api.resource;

import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.belongingsfinder.api.dao.ModelDAO;
import com.belongingsfinder.api.model.CategoryModel;
import com.google.inject.Inject;

/**
 * @author finbarr
 * 
 */
public class CategoryModelServerResource extends ServerResource {

	private final ModelDAO<CategoryModel> modelDAO;

	@Inject
	public CategoryModelServerResource(ModelDAO<CategoryModel> modelDAO) {
		this.modelDAO = modelDAO;
	}

	@Delete
	public void deleteCategory() {
		if (!modelDAO.del(getId())) {
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND, "No such entity");
		}
	}

	@Get("json")
	public CategoryModel getCategory() {
		return modelDAO.retrieve(getId());
	}

	@Put("json")
	public void updateCategory(CategoryModel model) {
		model.setId(getId());
		modelDAO.update(model);
	}

	private String getId() {
		return getRequest().getAttributes().get("id").toString();
	}

}
