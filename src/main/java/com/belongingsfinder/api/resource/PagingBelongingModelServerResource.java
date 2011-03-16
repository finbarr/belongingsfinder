package com.belongingsfinder.api.resource;

import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.pager.ModelPager;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * @author finbarr
 * 
 */
public class PagingBelongingModelServerResource extends ServerResource {

	private final ModelPager<BelongingModel> foundPager;
	private final ModelPager<BelongingModel> lostPager;

	@Inject
	public PagingBelongingModelServerResource(@Named("found") ModelPager<BelongingModel> foundPager,
			@Named("lost") ModelPager<BelongingModel> lostPager) {
		this.foundPager = foundPager;
		this.lostPager = lostPager;
	}

	@Get("json")
	public List<BelongingModel> getBelongings() {
		final int number = Integer.parseInt(getRequest().getAttributes().get("number").toString());
		final int offset = Integer.parseInt(getRequest().getAttributes().get("offset").toString());
		final String type = getRequest().getAttributes().get("type").toString();
		if (type.equals("found")) {
			return foundPager.retrieve(number, offset);
		} else if (type.equals("lost")) {
			return lostPager.retrieve(number, offset);
		}
		return null;
	}

}
