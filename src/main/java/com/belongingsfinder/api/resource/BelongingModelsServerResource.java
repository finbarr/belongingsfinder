package com.belongingsfinder.api.resource;

import java.util.Date;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.Post;

import com.belongingsfinder.api.dao.ModelDAO;
import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.model.S3FileModel;
import com.google.inject.Inject;

/**
 * @author finbarr
 * 
 */
public class BelongingModelsServerResource extends ValidatedServerResource {

	private final ModelDAO<BelongingModel> modelDAO;
	private final ModelDAO<S3FileModel> fileDAO;

	@Inject
	public BelongingModelsServerResource(ModelDAO<BelongingModel> modelDAO, ModelDAO<S3FileModel> fileDAO) {
		this.modelDAO = modelDAO;
		this.fileDAO = fileDAO;
	}

	@Post("json")
	public String createBelonging(BelongingModel model) {
		model.setLastUpdated(new Date());
		for (S3FileModel s3 : model.getImages()) {
			fileDAO.create(s3);
		}
		return modelDAO.create(model);
	}

	@Get("json")
	public List<BelongingModel> getBelongings() {
		return modelDAO.retrieveAll();
	}

}
