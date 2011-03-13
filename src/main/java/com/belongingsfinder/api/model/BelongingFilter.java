package com.belongingsfinder.api.model;

import java.io.Serializable;

public class BelongingFilter implements Serializable {

	private static final long serialVersionUID = 3031305247570478690L;

	private int maxResults;
	private int offset;
	private BelongingModel.Type type;
	private LatLon location;
	private String categoryId;
	private String terms;

	public BelongingFilter() {
	}

	public String getCategoryId() {
		return categoryId;
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

	public BelongingModel.Type getType() {
		return type;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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

	public void setType(BelongingModel.Type type) {
		this.type = type;
	}

}
