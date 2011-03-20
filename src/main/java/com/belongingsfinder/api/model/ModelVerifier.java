package com.belongingsfinder.api.model;

public interface ModelVerifier<T extends Model<T>> {

	public boolean verify(T model);

}
