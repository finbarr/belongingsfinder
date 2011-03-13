package com.belongingsfinder.api.resource;

import java.util.UUID;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.model.CategoryModel;
import com.belongingsfinder.api.model.LatLon;

public class StuffServerResource extends ServerResource {

	@Get("json")
	public BelongingModel getModel() {
		BelongingModel bm = new BelongingModel();
		bm.setId(UUID.randomUUID().toString());
		bm.setImageUrl("http://google.co.uk");
		bm.setLocation(new LatLon("-3,-4"));
		bm.setDescription("Hello, Description!");
		bm.setType(BelongingModel.Type.FOUND);
		CategoryModel cm = new CategoryModel();
		cm.setId(UUID.randomUUID().toString());
		cm.setName("yoyoyo");
		bm.setCategory(cm);
		return bm;
	}

}
