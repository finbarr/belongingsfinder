package com.belongingsfinder.api.model;

import java.io.Serializable;

public class BelongingFilter implements Serializable {

	private static final long serialVersionUID = 3031305247570478690L;

	private String search;
	private int maxResults;
	private int offset;
	private BelongingModel.Type type;
	private LatLon location;
	private CategoryModel category;

	public BelongingFilter() {
	}

	public CategoryModel getCategory() {
		return category;
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

	public String getSearch() {
		return search;
	}

	public BelongingModel.Type getType() {
		return type;
	}

	public void setCategory(CategoryModel category) {
		this.category = category;
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

	public void setSearch(String search) {
		this.search = search;
	}

	public void setType(BelongingModel.Type type) {
		this.type = type;
	}

}
