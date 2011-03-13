package com.belongingsfinder.api.resource;

import java.util.List;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.belongingsfinder.api.dao.ModelDAO;
import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.util.URIValidator;
import com.google.inject.Inject;

public class BelongingModelsServerResource extends ServerResource {

	private final ModelDAO<BelongingModel> modelDAO;

	@Inject
	public BelongingModelsServerResource(ModelDAO<BelongingModel> modelDAO) {
		this.modelDAO = modelDAO;
	}

	@Post("json")
	public String createBelonging(BelongingModel model) {
		if (URIValidator.isValid(model.getImageUrl())) {
			return modelDAO.create(model);
		} else {
			getResponse().setStatus(Status.CLIENT_ERROR_PRECONDITION_FAILED, "Invalid image URI");
			return null;
		}
	}

	@Get("json")
	public List<BelongingModel> getBelongings() {
		return modelDAO.retrieveAll();
	}

}
