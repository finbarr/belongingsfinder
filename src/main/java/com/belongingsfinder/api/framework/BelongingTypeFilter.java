package com.belongingsfinder.api.framework;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.routing.Filter;

public class BelongingTypeFilter extends Filter {

	@Override
	protected int beforeHandle(Request request, Response response) {
		final String type = request.getAttributes().get("type").toString();
		if (type.equals("found") || type.equals("lost")) {
			return Filter.CONTINUE;
		}
		response.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
		return Filter.STOP;
	}

}
