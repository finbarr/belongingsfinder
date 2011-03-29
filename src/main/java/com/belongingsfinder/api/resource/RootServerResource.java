package com.belongingsfinder.api.resource;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class RootServerResource extends ServerResource {

	@Get
	public String getRoot() {
		return "BelongingsFinder API version 1";
	}

}
