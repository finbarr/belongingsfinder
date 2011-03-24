package com.belongingsfinder.api.search;

import java.io.Serializable;
import java.util.List;

import com.belongingsfinder.api.model.BelongingModel;

public class BelongingSearchResult implements Serializable {

	private static final long serialVersionUID = -9203119345938042543L;

	private int totalResults;
	private List<BelongingModel> results;

	public BelongingSearchResult() {
	}

	public BelongingSearchResult(int totalResults, List<BelongingModel> results) {
		this.totalResults = totalResults;
		this.results = results;
	}

	public List<BelongingModel> getResults() {
		return results;
	}

	public int getTotalResults() {
		return totalResults;
	}

	public void setResults(List<BelongingModel> results) {
		this.results = results;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

}
