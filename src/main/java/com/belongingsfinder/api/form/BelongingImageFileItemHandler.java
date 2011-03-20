package com.belongingsfinder.api.form;

import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.restlet.data.MediaType;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.belongingsfinder.api.aws.S3File;
import com.belongingsfinder.api.aws.S3Store;
import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.model.ModelVerifier;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class BelongingImageFileItemHandler implements FileItemHandler<BelongingModel> {

	private final S3Store store;
	private final Map<String, MediaType> images;
	private final ModelVerifier<BelongingModel> verifier;

	@Inject
	private BelongingImageFileItemHandler(S3Store store, @Named("images") Map<String, MediaType> images,
			ModelVerifier<BelongingModel> verifier) {
		this.store = store;
		this.images = images;
		this.verifier = verifier;
	}

	@Override
	public void handle(FileItem fileItem, BelongingModel model)
			throws com.belongingsfinder.api.form.FileItemHandler.FileItemHandlerException {
		byte[] b = fileItem.get();
		if (b.length == 0) {
			throw new FileItemHandlerException(fileItem.getName() + " cannot be empty");
		}
		if (!images.containsKey(fileItem.getContentType())) {
			throw new FileItemHandlerException(fileItem.getName() + " invalid format");
		}
		ObjectMetadata omd = new ObjectMetadata();
		omd.setContentLength(b.length);
		omd.setContentType(fileItem.getContentType());
		ByteArrayInputStream bis = new ByteArrayInputStream(b);
		String ext = images.get(fileItem.getContentType()).equals(MediaType.IMAGE_JPEG) ? ".jpeg" : ".png";
		String key = UUID.randomUUID().toString() + ext;
		// dirty hack
		model.setImage(new S3File());
		if (verifier.verify(model)) {
			model.setImage(store.store(key, bis, omd, CannedAccessControlList.PublicRead));
		}
	}

}
