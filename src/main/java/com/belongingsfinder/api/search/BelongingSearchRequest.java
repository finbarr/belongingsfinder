package com.belongingsfinder.api.search;

import java.io.Serializable;

import com.belongingsfinder.api.i18n.HasLanguage;
import com.belongingsfinder.api.i18n.Language;
import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.model.LatLon;
import com.belongingsfinder.api.model.BelongingModel.BelongingType;

/**
 * @author finbarr
 * 
 */
public class BelongingSearchRequest implements HasLanguage, Serializable {

	private static final long serialVersionUID = 3031305247570478690L;

	private int maxResults;
	private int offset;
	private BelongingModel.BelongingType type;
	private LatLon location;
	private String categoryId;
	private String terms;
	private Language language;

	public String getCategoryId() {
		return categoryId;
	}

	public Language getLanguage() {
		return language;
	}

	public LatLon getLocation() {
		return location;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public int getOffset() {
		return offset;
	}

	public String getTerms() {
		return terms;
	}

	public BelongingModel.BelongingType getType() {
		return type;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public void setLocation(LatLon location) {
		this.location = location;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public void setType(BelongingModel.BelongingType type) {
		this.type = type;
	}

}
