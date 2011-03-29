package com.belongingsfinder.api.modules;

import org.restlet.data.MediaType;

import com.belongingsfinder.api.form.BelongingCategoryFieldFileItemHandler;
import com.belongingsfinder.api.form.BelongingDescriptionFieldFileItemHandler;
import com.belongingsfinder.api.form.BelongingEmailFieldFileItemHandler;
import com.belongingsfinder.api.form.BelongingImageFileItemHandler;
import com.belongingsfinder.api.form.BelongingLanguageFieldFileItemHandler;
import com.belongingsfinder.api.form.BelongingLocationFieldFileItemHandler;
import com.belongingsfinder.api.form.BelongingTypeFieldFileItemHandler;
import com.belongingsfinder.api.form.FileItemHandler;
import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.model.BelongingModel.BelongingField;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.name.Names;

public class FileItemHandlerModule extends AbstractModule {

	@Override
	protected void configure() {
		MapBinder<String, FileItemHandler<BelongingModel>> binder = MapBinder.newMapBinder(binder(),
				new TypeLiteral<String>() {
				}, new TypeLiteral<FileItemHandler<BelongingModel>>() {
				});
		binder.addBinding(BelongingField.DESCRIPTION.toString()).to(BelongingDescriptionFieldFileItemHandler.class);
		binder.addBinding(BelongingField.LOCATION.toString()).to(BelongingLocationFieldFileItemHandler.class);
		binder.addBinding(BelongingField.EMAIL.toString()).to(BelongingEmailFieldFileItemHandler.class);
		binder.addBinding(BelongingField.CATEGORY.toString()).to(BelongingCategoryFieldFileItemHandler.class);
		binder.addBinding(BelongingField.IMAGE.toString()).to(BelongingImageFileItemHandler.class);
		binder.addBinding(BelongingField.TYPE.toString()).to(BelongingTypeFieldFileItemHandler.class);
		binder.addBinding(BelongingField.LANGUAGE.toString()).to(BelongingLanguageFieldFileItemHandler.class);
		MapBinder<String, MediaType> imageTypes = MapBinder.newMapBinder(binder(), String.class, MediaType.class,
				Names.named("images"));
		mediaType(imageTypes, MediaType.IMAGE_JPEG);
		mediaType(imageTypes, MediaType.IMAGE_PNG);
	}

	private void mediaType(MapBinder<String, MediaType> mediaTypeBinder, MediaType mediaType) {
		mediaTypeBinder.addBinding(mediaType.getName()).toInstance(mediaType);
	}
}
