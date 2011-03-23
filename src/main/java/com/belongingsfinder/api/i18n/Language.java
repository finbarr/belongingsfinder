package com.belongingsfinder.api.i18n;

public enum Language {

	EN("en"), JP("jp");

	private final String name;

	private Language(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
