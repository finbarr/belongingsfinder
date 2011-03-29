package com.belongingsfinder.api.form;

import org.apache.commons.fileupload.FileItem;

import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.model.LatLon;

public class BelongingLocationFieldFileItemHandler extends FieldFileItemHandler<BelongingModel> {

	@Override
	protected void doHandle(FileItem fileItem, BelongingModel model)
			throws com.belongingsfinder.api.form.FileItemHandler.FileItemHandlerException {
		try {
			if (fileItem.getString() != null && fileItem.getString().length() > 0) {
				LatLon location = new LatLon(fileItem.getString());
				model.setLocation(location);
			}
		} catch (Exception e) {
			throw new FileItemHandlerException(fileItem.getName() + " must be a latitude longitude pair");
		}
	}

}
