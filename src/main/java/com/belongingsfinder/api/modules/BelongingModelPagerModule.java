package com.belongingsfinder.api.modules;

import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.model.BelongingModel.BelongingType;
import com.belongingsfinder.api.pager.BelongingModelPager;
import com.belongingsfinder.api.pager.ModelPager;
import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.PrivateModule;
import com.google.inject.name.Names;

/**
 * @author finbarr
 * 
 */
public class BelongingModelPagerModule extends AbstractModule {

	@Override
	protected void configure() {
		for (BelongingType type : BelongingModel.BelongingType.values()) {
			bindType(type);
		}
	}

	private void bindType(final BelongingType type) {
		install(new PrivateModule() {
			@SuppressWarnings("unchecked")
			@Override
			protected void configure() {
				Key<ModelPager<BelongingModel>> key = (Key<ModelPager<BelongingModel>>) Key.get(
						com.google.inject.util.Types.newParameterizedType(ModelPager.class, BelongingModel.class),
						Names.named(type.getName()));
				bind(key).to(BelongingModelPager.class);
				bind(BelongingModel.BelongingType.class).toInstance(type);
				expose(key);
			}
		});
	}

}
