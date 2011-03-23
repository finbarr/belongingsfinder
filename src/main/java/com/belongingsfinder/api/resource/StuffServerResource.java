package com.belongingsfinder.api.resource;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.belongingsfinder.api.dao.ModelDAO;
import com.belongingsfinder.api.i18n.Language;
import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.model.BelongingModel.BelongingType;
import com.belongingsfinder.api.model.CategoryModel;
import com.google.inject.Inject;

/**
 * @author finbarr
 * 
 */
public class StuffServerResource extends ServerResource {

	@Inject
	private ModelDAO<BelongingModel> dao;

	@Inject
	private ModelDAO<CategoryModel> cdao;

	@Get("json")
	public BelongingModel getModel() {
		CategoryModel cm = new CategoryModel();
		cm.setName("parent");
		List<CategoryModel> children = new LinkedList<CategoryModel>();
		children.add(c("a", cm));
		children.add(c("b", cm));
		children.add(c("c", cm));
		children.add(c("d", cm));
		cm.setChildren(children);
		cm.getChildren().get(0).setName("updated");
		cdao.create(cm);
		BelongingModel b = new BelongingModel();
		b.setCategory(cm);
		b.setDescription("Hello, World!");
		b.setEmail("f@fbar.org");
		b.setLanguage(Language.EN);
		b.setLastUpdated(new Date());
		b.setType(BelongingType.LOST);
		dao.create(b);
		return b;
	}

	private CategoryModel c(String name, CategoryModel parent) {
		CategoryModel cm = new CategoryModel();
		cm.setName(name);
		cm.setParent(parent);
		return cm;
	}
}
