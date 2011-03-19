package com.belongingsfinder.api.resource;

import java.io.File;
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

import com.belongingsfinder.api.form.FileItemHandler;
import com.belongingsfinder.api.form.FileItemHandler.FileItemHandlerException;
import com.belongingsfinder.api.model.BelongingModel;
import com.google.inject.Inject;

public class MobileBelongingModelServerResource extends ServerResource {

	private final String temp;
	private final Map<String, FileItemHandler<BelongingModel>> handlers;
	private final Logger logger;

	@Inject
	public MobileBelongingModelServerResource(String temp, Map<String, FileItemHandler<BelongingModel>> handlers,
			Logger logger) {
		this.temp = temp;
		this.handlers = handlers;
		this.logger = logger;
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
				while (iterator.hasNext()) {
					FileItem fileItem = iterator.next();
					if (handlers.containsKey(fileItem.getFieldName())) {
						handlers.get(fileItem.getFieldName()).handle(fileItem, model);
					} else {
						logger.log(Level.INFO, "No FileItemHandler bound for " + fileItem.getName());
					}
				}
			} catch (FileUploadException e) {
				getResponse().setStatus(Status.SERVER_ERROR_INTERNAL, "file upload exception");
			} catch (FileItemHandlerException e) {
				getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST, e.getMessage());
			}
		} else {
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
		}
	}

}
