package com.belongingsfinder.api.framework;

import java.util.UUID;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.routing.Filter;

import com.google.inject.Inject;
import com.sun.istack.internal.Nullable;

/**
 * @author Finbarr
 * 
 */
public class UUIDFilter extends Filter {

	@Inject
	public UUIDFilter(@Nullable Context context) {
		super(context);
	}

	@Override
	protected int beforeHandle(Request request, Response response) {
		final String uuid = request.getAttributes().get("id").toString();
		try {
			UUID.fromString(uuid);
		} catch (IllegalArgumentException e) {
			response.setStatus(Status.CLIENT_ERROR_UNPROCESSABLE_ENTITY, "Invalid id \"" + uuid + "\"");
			return Filter.STOP;
		}
		return Filter.CONTINUE;
	}

}
