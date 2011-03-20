package com.belongingsfinder.api.modules;

import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.model.ModelVerifier;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

public class ModelModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(new TypeLiteral<ModelVerifier<BelongingModel>>() {
		}).toInstance(new BelongingModel.BelongingModelVerifier());
	}

}
