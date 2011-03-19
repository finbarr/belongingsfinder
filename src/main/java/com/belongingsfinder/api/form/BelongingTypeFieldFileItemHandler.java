package com.belongingsfinder.api.form;

import org.apache.commons.fileupload.FileItem;

import com.belongingsfinder.api.model.BelongingModel;

public class BelongingTypeFieldFileItemHandler extends FieldFileItemHandler<BelongingModel> {

	@Override
	protected void doHandle(FileItem fileItem, BelongingModel model)
			throws com.belongingsfinder.api.form.FileItemHandler.FileItemHandlerException {
		final String error = " must be lost or found";
		if (fileItem.getString() == null || fileItem.getString().length() == 0) {
			throw new FileItemHandlerException(fileItem.getName() + error);
		}
		if (fileItem.getString().equalsIgnoreCase("lost")) {
			model.setType(BelongingModel.BelongingType.LOST);
		} else if (fileItem.getString().equalsIgnoreCase("found")) {
			model.setType(BelongingModel.BelongingType.FOUND);
		} else {
			throw new FileItemHandlerException(fileItem.getName() + error);
		}
	}

}
