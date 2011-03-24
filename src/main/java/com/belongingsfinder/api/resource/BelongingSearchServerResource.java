package com.belongingsfinder.api.resource;

import java.util.List;

import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.model.BelongingSearchRequest;
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
		if (request.getLanguage() == null || request.getTerms() == null || request.getTerms().length() == 0) {
			getResponse().setStatus(Status.CLIENT_ERROR_UNPROCESSABLE_ENTITY, "Invalid search request");
			return null;
		}
		return search.search(request);
	}

}
