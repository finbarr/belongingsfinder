package com.belongingsfinder.api.form;

import org.apache.commons.fileupload.FileItem;

import com.belongingsfinder.api.model.BelongingModel;

public class BelongingDescriptionFieldFileItemHandler extends FieldFileItemHandler<BelongingModel> {

	@Override
	protected void doHandle(FileItem fileItem, BelongingModel model)
			throws com.belongingsfinder.api.form.FileItemHandler.FileItemHandlerException {
		model.setDescription(fileItem.getString());
	}

}
