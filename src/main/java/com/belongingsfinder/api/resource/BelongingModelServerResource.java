package com.belongingsfinder.api.resource;

import java.util.Date;

import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.belongingsfinder.api.dao.ModelDAO;
import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.util.URIValidator;
import com.google.inject.Inject;

public class BelongingModelServerResource extends ServerResource {

	private final ModelDAO<BelongingModel> modelDAO;

	@Inject
	public BelongingModelServerResource(ModelDAO<BelongingModel> modelDAO) {
		this.modelDAO = modelDAO;
	}

	@Delete("json")
	public void deleteBelonging() {
		if (!modelDAO.del(getId())) {
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND, "No such entity");
		}
	}

	@Get("json")
	public BelongingModel getBelonging() {
		BelongingModel model = modelDAO.retrieve(getId());
		if (model == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND, "No such entity");
		}
		return model;
	}

	@Put("json")
	public void updateBelonging(BelongingModel model) {
		if (URIValidator.isValid(model.getImageUrl())) {
			model.setId(getId());
			model.setLastUpdated(new Date());
			modelDAO.update(model);
		} else {
			getResponse().setStatus(Status.CLIENT_ERROR_PRECONDITION_FAILED, "Invalid image URI");
		}
	}

	private String getId() {
		return getRequest().getAttributes().get("id").toString();
	}

}
