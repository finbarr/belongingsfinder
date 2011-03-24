package com.belongingsfinder.api.resource;

import java.util.Set;

import org.restlet.data.Status;
import org.restlet.resource.Get;

import com.belongingsfinder.api.dao.ModelDAO;
import com.belongingsfinder.api.model.CategoryModel;
import com.google.inject.Inject;

/**
 * @author finbarr
 * 
 */
public class ChildrenCategoryModelServerResource extends ValidatedServerResource {

	private final ModelDAO<CategoryModel> categoryDAO;

	@Inject
	public ChildrenCategoryModelServerResource(ModelDAO<CategoryModel> categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	@Get("json")
	public Set<CategoryModel> getChildren() {
		CategoryModel cm = categoryDAO.retrieve(getId());
		if (cm == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND, "No such category");
			return null;
		}
		return cm.getChildren();
	}

	private String getId() {
		return getRequest().getAttributes().get("id").toString();
	}

}
