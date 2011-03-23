package com.belongingsfinder.api.resource;

import java.util.Date;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.belongingsfinder.api.dao.ModelDAO;
import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.model.BelongingModel.BelongingLang;
import com.google.inject.Inject;

/**
 * @author finbarr
 * 
 */
public class BelongingModelsServerResource extends ServerResource {

	private final ModelDAO<BelongingModel> modelDAO;

	@Inject
	public BelongingModelsServerResource(ModelDAO<BelongingModel> modelDAO) {
		this.modelDAO = modelDAO;
	}

	@Post("json")
	public String createBelonging(BelongingModel model) {
		if (model.getLang() == null) {
			// TODO use Google translate to detect
			model.setLang(BelongingLang.JP);
		}
		model.setLastUpdated(new Date());
		return modelDAO.create(model);
	}

	@Get("json")
	public List<BelongingModel> getBelongings() {
		return modelDAO.retrieveAll();
	}

}
