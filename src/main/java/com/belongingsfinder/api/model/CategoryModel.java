package com.belongingsfinder.api.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CategoryModel implements Model<CategoryModel>, Serializable {

	private static final long serialVersionUID = -4520507504770029807L;

	@Id
	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
