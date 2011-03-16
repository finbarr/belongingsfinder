package com.belongingsfinder.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import com.sun.istack.internal.NotNull;

/**
 * @author finbarr
 * 
 */
@Entity
public class CategoryModel implements Model<CategoryModel>, Serializable {

	private static final long serialVersionUID = -4520507504770029807L;

	@Id
	@Field(index = Index.UN_TOKENIZED, store = Store.NO)
	private String id;
	@NotNull
	@Column(unique = true)
	private String name;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne
	private CategoryModel parent;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@JsonIgnore
	public CategoryModel getParent() {
		return parent;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public void setParent(CategoryModel parent) {
		this.parent = parent;
	}

}
