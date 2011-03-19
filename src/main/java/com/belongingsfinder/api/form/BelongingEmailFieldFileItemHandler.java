package com.belongingsfinder.api.form;

import org.apache.commons.fileupload.FileItem;

import com.belongingsfinder.api.model.BelongingModel;

public class BelongingEmailFieldFileItemHandler extends FieldFileItemHandler<BelongingModel> {

	@Override
	protected void doHandle(FileItem fileItem, BelongingModel model)
			throws com.belongingsfinder.api.form.FileItemHandler.FileItemHandlerException {
		if (fileItem.getString() != null && fileItem.getString().contains("@") && fileItem.getString().contains(".")) {
			model.setEmail(fileItem.getString());
		} else {
			throw new FileItemHandlerException(fileItem.getName() + " does not appear to be a valid email");
		}
	}
}
