package com.belongingsfinder.api.resource;

import java.util.Date;

import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

import com.belongingsfinder.api.dao.ModelDAO;
import com.belongingsfinder.api.model.BelongingModel;
import com.google.inject.Inject;

/**
 * @author finbarr
 * 
 */
public class BelongingModelServerResource extends ValidatedServerResource {

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
		model.setId(getId());
		model.setLastUpdated(new Date());
		modelDAO.update(model);
	}

	private String getId() {
		return getRequest().getAttributes().get("id").toString();
	}

}
