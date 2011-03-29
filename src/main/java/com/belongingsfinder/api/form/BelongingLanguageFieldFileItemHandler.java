package com.belongingsfinder.api.form;

import org.apache.commons.fileupload.FileItem;

import com.belongingsfinder.api.i18n.Language;
import com.belongingsfinder.api.model.BelongingModel;

public class BelongingLanguageFieldFileItemHandler implements FileItemHandler<BelongingModel> {

	@Override
	public void handle(FileItem fileItem, BelongingModel model)
			throws com.belongingsfinder.api.form.FileItemHandler.FileItemHandlerException {
		if (fileItem.getString() != null && fileItem.getString().length() > 0) {
			String language = fileItem.getString();
			for (Language l : Language.values()) {
				if (l.toString().equals(language.toLowerCase())) {
					model.setLanguage(l);
				}
			}
		}

	}

}
