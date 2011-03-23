package com.belongingsfinder.api.resource;

import java.util.LinkedList;
import java.util.List;

import org.restlet.data.Status;
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
		if (cm == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND, "No such category");
			return null;
		}
		return cm.getChildren() == null ? new LinkedList<CategoryModel>() : cm.getChildren();
	}

	private String getId() {
		return getRequest().getAttributes().get("id").toString();
	}

}
