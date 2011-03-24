package com.belongingsfinder.api.resource;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class ValidatedServerResource extends ServerResource {

	@Override
	protected void doCatch(Throwable throwable) {
		if (throwable instanceof ResourceException && throwable.getCause() instanceof RollbackException
				&& throwable.getCause().getCause() instanceof ConstraintViolationException) {
			getResponse().setStatus(Status.CLIENT_ERROR_UNPROCESSABLE_ENTITY, "Invalid entity");
		} else {
			super.doCatch(throwable);
		}
	}

}
