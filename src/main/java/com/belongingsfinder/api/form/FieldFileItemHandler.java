package com.belongingsfinder.api.form;

import org.apache.commons.fileupload.FileItem;

import com.belongingsfinder.api.model.Model;

public abstract class FieldFileItemHandler<T extends Model<T>> implements FileItemHandler<T> {

	@Override
	public void handle(FileItem fileItem, T model) throws FileItemHandlerException {
		if (fileItem.isFormField()) {
			doHandle(fileItem, model);
		} else {
			throw new FileItemHandlerException(fileItem.getFieldName() + " should be a field");
		}
	}

	protected abstract void doHandle(FileItem fileItem, T model) throws FileItemHandlerException;

}
