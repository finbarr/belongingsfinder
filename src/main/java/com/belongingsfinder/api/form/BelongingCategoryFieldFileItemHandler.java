package com.belongingsfinder.api.form;

import org.apache.commons.fileupload.FileItem;

import com.belongingsfinder.api.dao.ModelDAO;
import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.model.CategoryModel;
import com.google.inject.Inject;

public class BelongingCategoryFieldFileItemHandler extends FieldFileItemHandler<BelongingModel> {

	private final ModelDAO<CategoryModel> categoryDAO;

	@Inject
	public BelongingCategoryFieldFileItemHandler(ModelDAO<CategoryModel> categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	@Override
	protected void doHandle(FileItem fileItem, BelongingModel model)
			throws com.belongingsfinder.api.form.FileItemHandler.FileItemHandlerException {
		if (fileItem.getString() != null && fileItem.getString().length() > 0) {
			CategoryModel category = categoryDAO.retrieve(fileItem.getString());
			if (category == null) {
				throw new FileItemHandlerException(fileItem.getName() + " is not a valid category id");
			}
			model.setCategory(category);
		}
	}

}
