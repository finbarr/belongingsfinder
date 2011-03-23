package com.belongingsfinder.api.resource;

import java.util.LinkedList;
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
public class StuffServerResource extends ServerResource {

	@Inject
	private ModelDAO<CategoryModel> dao;

	@Get("json")
	public CategoryModel getModel() {
		CategoryModel cm = new CategoryModel();
		cm.setName("parent");
		List<CategoryModel> children = new LinkedList<CategoryModel>();
		children.add(c("a", cm));
		children.add(c("b", cm));
		children.add(c("c", cm));
		children.add(c("d", cm));
		cm.setChildren(children);
		dao.create(cm);
		cm.getChildren().get(0).setName("updated");
		dao.update(cm);
		return cm;
	}

	private CategoryModel c(String name, CategoryModel parent) {
		CategoryModel cm = new CategoryModel();
		cm.setName(name);
		cm.setParent(parent);
		return cm;
	}
}
