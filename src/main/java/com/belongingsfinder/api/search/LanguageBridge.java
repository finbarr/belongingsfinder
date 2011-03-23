package com.belongingsfinder.api.search;

import org.apache.lucene.document.Document;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;

import com.belongingsfinder.api.i18n.HasLanguage;
import com.belongingsfinder.api.i18n.Language;

public class LanguageBridge implements FieldBridge {

	@Override
	public void set(String name, Object value, Document document, LuceneOptions luceneOptions) {
		if (value instanceof HasLanguage) {
			Language language = ((HasLanguage) value).getLanguage();
			String fieldName = new StringBuilder(name).append("_").append(language.toString()).toString();
			luceneOptions.addFieldToDocument(fieldName, value.toString(), document);
		} else {
			throw new IllegalStateException("LanguageBridge can only be used for HasLanguage instances!");
		}
	}

}