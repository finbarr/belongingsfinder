package com.belongingsfinder.api.resource;

import java.util.List;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.belongingsfinder.api.model.BelongingSearchRequest;
import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.search.BelongingModelSearch;
import com.google.inject.Inject;

/**
 * @author finbarr
 * 
 */
public class BelongingSearchServerResource extends ServerResource {

	private final BelongingModelSearch search;

	@Inject
	public BelongingSearchServerResource(BelongingModelSearch search) {
		this.search = search;
	}

	@Post("json")
	public List<BelongingModel> findBelongings(BelongingSearchRequest request) {
		return search.search(request);
	}

}
