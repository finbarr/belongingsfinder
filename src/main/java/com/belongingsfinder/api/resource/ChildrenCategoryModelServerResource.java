package com.belongingsfinder.api.resource;

import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.belongingsfinder.api.dao.ModelDAO;
import com.belongingsfinder.api.model.CategoryModel;
import com.google.inject.Inject;

/**
 * @author finbarr
 * 
 */
public class ChildrenCategoryModelServerResource extends ServerResource {

	private final ModelDAO<CategoryModel> categoryDAO;

	@Inject
	public ChildrenCategoryModelServerResource(ModelDAO<CategoryModel> categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	@Get("json")
	public List<CategoryModel> getChildren() {
		CategoryModel cm = categoryDAO.retrieve(getId());
		return cm == null ? null : cm.getChildren();
	}

	private String getId() {
		return getRequest().getAttributes().get("id").toString();
	}

}
