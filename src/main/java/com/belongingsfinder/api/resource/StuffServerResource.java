package com.belongingsfinder.api.resource;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
public class StuffServerResource extends ValidatedServerResource {

	@Inject
	private ModelDAO<CategoryModel> cdao;

	// totally not in line with REST conventions
	@Get
	public void createCategories() {
		List<CategoryModel> categories = new LinkedList<CategoryModel>();
		categories.add(setChildren(createCategory("Clothing"), "Clothes", "Shoes", "Accessories", "Other"));
		categories.add(setChildren(createCategory("Jewellery"), "Rings", "Watches", "Necklaces", "Other"));
		categories.add(createCategory("Cars/Vehicles"));
		categories.add(createCategory("Bicycles"));
		categories.add(createCategory("Artwork/Sculpture"));
		categories.add(createCategory("Personal/Photographs"));
		categories.add(setChildren(createCategory("Electricals"), "Laptops/Desktops", "Mobile phones", "Cameras",
				"Other"));
		categories.add(createCategory("Furniture"));
		categories.add(createCategory("Dolls/Bears"));
		categories.add(createCategory("Books/Diaries"));
		categories.add(createCategory("Musical Instruments"));
		categories.add(createCategory("Other"));
		for (CategoryModel c : categories) {
			cdao.create(c);
		}
		getResponse().setStatus(Status.SUCCESS_NO_CONTENT);
	}

	private CategoryModel createCategory(String name) {
		CategoryModel c = new CategoryModel();
		c.setName(name);
		return c;
	}

	private CategoryModel setChildren(CategoryModel c, String... children) {
		Set<CategoryModel> kids = new HashSet<CategoryModel>();
		for (String s : children) {
			CategoryModel child = new CategoryModel();
			child.setName(s);
			child.setParent(c);
			kids.add(child);
		}
		c.setChildren(kids);
		return c;
	}
}
