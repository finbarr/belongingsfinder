package com.belongingsfinder.api.resource;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class PingServerResource extends ServerResource {

	@Get
	public String ping() {
		return "ping";
	}

}
