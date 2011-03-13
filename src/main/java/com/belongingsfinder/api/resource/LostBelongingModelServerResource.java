package com.belongingsfinder.api.resource;

import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.pager.ModelPager;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class LostBelongingModelServerResource extends ServerResource {

	private final ModelPager<BelongingModel> pager;

	@Inject
	public LostBelongingModelServerResource(@Named("lost") ModelPager<BelongingModel> pager) {
		this.pager = pager;
	}

	@Get("json")
	public List<BelongingModel> getLostBelongings() {
		final int number = (Integer) getRequest().getAttributes().get("number");
		final int offset = (Integer) getRequest().getAttributes().get("offset");
		return pager.retrieve(number, offset);
	}

}
