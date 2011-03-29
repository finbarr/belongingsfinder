package com.belongingsfinder.api.form;

import org.apache.commons.fileupload.FileItem;

import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.model.CategoryModel;

public class BelongingCategoryFieldFileItemHandler extends FieldFileItemHandler<BelongingModel> {

	@Override
	protected void doHandle(FileItem fileItem, BelongingModel model)
			throws com.belongingsfinder.api.form.FileItemHandler.FileItemHandlerException {
		if (fileItem.getString() != null && fileItem.getString().length() > 0) {
			CategoryModel c = new CategoryModel();
			c.setId(fileItem.getString());
			model.setCategory(c);
		}
	}

}
