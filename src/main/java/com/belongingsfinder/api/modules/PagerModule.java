package com.belongingsfinder.api.modules;

import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.pager.BelongingModelPager;
import com.belongingsfinder.api.pager.ModelPager;
import com.google.inject.AbstractModule;
import com.google.inject.PrivateModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

public class PagerModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new PrivateModule() {
			@Override
			protected void configure() {
				bind(new TypeLiteral<ModelPager<BelongingModel>>() {
				}).annotatedWith(Names.named("found")).to(BelongingModelPager.class);
				bind(BelongingModel.Type.class).toInstance(BelongingModel.Type.FOUND);
				expose(new TypeLiteral<ModelPager<BelongingModel>>() {
				});
			}
		});
		install(new PrivateModule() {
			@Override
			protected void configure() {
				bind(new TypeLiteral<ModelPager<BelongingModel>>() {
				}).annotatedWith(Names.named("lost")).to(BelongingModelPager.class);
				bind(BelongingModel.Type.class).toInstance(BelongingModel.Type.LOST);
				expose(new TypeLiteral<ModelPager<BelongingModel>>() {
				});
			}
		});
	}

}
