package com.belongingsfinder.api.form;

import org.apache.commons.fileupload.FileItem;

import com.belongingsfinder.api.model.Model;

public interface FileItemHandler<T extends Model<T>> {

	public void handle(FileItem fileItem, T model) throws FileItemHandlerException;

	public static class FileItemHandlerException extends Exception {

		private static final long serialVersionUID = -5514898018447129211L;

		public FileItemHandlerException(String message) {
			super(message);
		}

	}

}
