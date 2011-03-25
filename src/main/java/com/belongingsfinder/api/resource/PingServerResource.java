package com.belongingsfinder.api.resource;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class PingServerResource extends ServerResource {

	@Get
	public void ping() {
		getResponse().setStatus(Status.SUCCESS_NO_CONTENT);
	}

}
