package com.belongingsfinder.api.modules;

import org.restlet.data.MediaType;

import com.belongingsfinder.api.form.BelongingCategoryFieldFileItemHandler;
import com.belongingsfinder.api.form.BelongingDescriptionFieldFileItemHandler;
import com.belongingsfinder.api.form.BelongingEmailFieldFileItemHandler;
import com.belongingsfinder.api.form.BelongingImageFileItemHandler;
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
		binder.addBinding(BelongingField.DESCRIPTION.getName()).to(BelongingDescriptionFieldFileItemHandler.class);
		binder.addBinding(BelongingField.LOCATION.getName()).to(BelongingLocationFieldFileItemHandler.class);
		binder.addBinding(BelongingField.EMAIL.getName()).to(BelongingEmailFieldFileItemHandler.class);
		binder.addBinding(BelongingField.CATEGORY.getName()).to(BelongingCategoryFieldFileItemHandler.class);
		binder.addBinding(BelongingField.IMAGE.getName()).to(BelongingImageFileItemHandler.class);
		binder.addBinding(BelongingField.TYPE.getName()).to(BelongingTypeFieldFileItemHandler.class);
		MapBinder<String, MediaType> imageTypes = MapBinder.newMapBinder(binder(), String.class, MediaType.class,
				Names.named("images"));
		mediaType(imageTypes, MediaType.IMAGE_JPEG);
		mediaType(imageTypes, MediaType.IMAGE_PNG);
	}

	private void mediaType(MapBinder<String, MediaType> mediaTypeBinder, MediaType mediaType) {
		mediaTypeBinder.addBinding(mediaType.getName()).toInstance(mediaType);
	}
}
