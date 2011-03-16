package com.belongingsfinder.api.resource;

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
public class BelongingModelCountServerResource extends ServerResource {

	private final ModelPager<BelongingModel> allPager;
	private final ModelPager<BelongingModel> foundPager;
	private final ModelPager<BelongingModel> lostPager;

	@Inject
	public BelongingModelCountServerResource(@Named("all") ModelPager<BelongingModel> allPager,
			@Named("found") ModelPager<BelongingModel> foundPager, @Named("lost") ModelPager<BelongingModel> lostPager) {
		this.allPager = allPager;
		this.foundPager = foundPager;
		this.lostPager = lostPager;
	}

	@Get
	public long getCount() {
		final String type = getRequest().getAttributes().get("type").toString();
		if (type.equals("all")) {
			return allPager.count();
		}
		if (type.equals("found")) {
			return foundPager.count();
		}
		if (type.equals("lost")) {
			return lostPager.count();
		}
		return 0;
	}

}
