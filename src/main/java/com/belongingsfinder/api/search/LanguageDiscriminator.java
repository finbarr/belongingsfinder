package com.belongingsfinder.api.search;

import org.hibernate.search.analyzer.Discriminator;

import com.belongingsfinder.api.i18n.HasLanguage;

public class LanguageDiscriminator implements Discriminator {

	@Override
	public String getAnalyzerDefinitionName(Object value, Object entity, String field) {
		if (entity instanceof HasLanguage) {
			HasLanguage h = (HasLanguage) entity;
			return h.getLanguage().toString();
		}
		throw new IllegalStateException("LanguageDiscriminator can only be used on HasLanguage instances!");
	}
}