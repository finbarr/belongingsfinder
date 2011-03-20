package com.belongingsfinder.api.resource;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.fileupload.RestletFileUpload;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.belongingsfinder.api.dao.ModelDAO;
import com.belongingsfinder.api.form.FileItemHandler;
import com.belongingsfinder.api.form.FileItemHandler.FileItemHandlerException;
import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.model.ModelVerifier;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class MobileBelongingModelServerResource extends ServerResource {

	private final String temp;
	private final Map<String, FileItemHandler<BelongingModel>> handlers;
	private final Logger logger;
	private final ModelVerifier<BelongingModel> verifier;
	private final ModelDAO<BelongingModel> belongingDAO;

	@Inject
	public MobileBelongingModelServerResource(@Named("temp") String temp,
			Map<String, FileItemHandler<BelongingModel>> handlers, Logger logger,
			ModelVerifier<BelongingModel> verifier, ModelDAO<BelongingModel> belongingDAO) {
		this.temp = temp;
		this.handlers = handlers;
		this.logger = logger;
		this.verifier = verifier;
		this.belongingDAO = belongingDAO;
	}

	@Post
	public void createBelonging(Representation representation) {
		if (MediaType.MULTIPART_FORM_DATA.equals(representation.getMediaType(), true)) {
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD,
						new File(temp));
				BelongingModel model = new BelongingModel();
				final Iterator<FileItem> iterator = new RestletFileUpload(factory).parseRequest(getRequest())
						.iterator();
				FileItem image = null;
				while (iterator.hasNext()) {
					FileItem fileItem = iterator.next();
					if (fileItem.getFieldName() != null) {
						if (handlers.containsKey(fileItem.getFieldName())
								&& !fileItem.getFieldName().equals(BelongingModel.BelongingField.IMAGE.getName())) {
							handlers.get(fileItem.getFieldName()).handle(fileItem, model);
						} else if (fileItem.getFieldName().equals(BelongingModel.BelongingField.IMAGE.getName())) {
							image = fileItem;
						} else {
							logger.log(Level.INFO, "No FileItemHandler bound for " + fileItem.getName());
						}
					}
				}
				if (image != null) {
					handlers.get(image.getFieldName()).handle(image, model);
				}
				if (verifier.verify(model)) {
					model.setLastUpdated(new Date());
					belongingDAO.create(model);
				} else {
					getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
				}
			} catch (FileUploadException e) {
				getResponse().setStatus(Status.SERVER_ERROR_INTERNAL, "file upload exception");
			} catch (FileItemHandlerException e) {
				getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST, e.getMessage());
			}
		} else {
			getResponse().setStatus(Status.CLIENT_ERROR_UNSUPPORTED_MEDIA_TYPE);
		}
	}
}
