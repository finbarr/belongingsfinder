package com.belongingsfinder.api.resource;

import java.util.UUID;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.belongingsfinder.api.dao.ModelDAO;
import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.model.CategoryModel;
import com.belongingsfinder.api.model.LatLon;
import com.google.inject.Inject;

public class StuffServerResource extends ServerResource {

	@Inject
	private ModelDAO<CategoryModel> categoryDAO;

	@Get("json")
	public BelongingModel getModel() {
		BelongingModel bm = new BelongingModel();
		bm.setId(UUID.randomUUID().toString());
		bm.setImageUrl("http://google.co.uk");
		bm.setLocation(new LatLon("-3,-4"));
		bm.setDescription("Hello, Description!");
		bm.setType(BelongingModel.Type.FOUND);
		CategoryModel cm = new CategoryModel();
		cm.setName("category-name");
		cm.setId(UUID.randomUUID().toString());
		bm.setCategory(cm);

		categoryDAO.update(cm);

		return bm;
	}

}
