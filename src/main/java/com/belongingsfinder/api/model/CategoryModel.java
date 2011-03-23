package com.belongingsfinder.api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;

import com.sun.istack.internal.NotNull;

/**
 * @author finbarr
 * 
 */
@Entity
public class CategoryModel implements Model<CategoryModel>, Serializable {

	private static final long serialVersionUID = -4520507504770029807L;

	@Id
	@GeneratedValue(generator = "bf-uuid")
	@GenericGenerator(name = "bf-uuid", strategy = "com.belongingsfinder.api.dao.jpa.UUIDIdentifierGenerator")
	@Field(index = Index.UN_TOKENIZED)
	private String id;
	@NotNull
	@Column(unique = true)
	private String name;
	@OneToMany(cascade = CascadeType.ALL)
	private List<CategoryModel> children;
	private boolean isChild;

	public List<CategoryModel> getChildren() {
		return children;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@JsonIgnore
	public boolean isChild() {
		return isChild;
	}

	public void setChildren(List<CategoryModel> children) {
		this.children = children;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonIgnore
	public void setIsChild(boolean isChild) {
		this.isChild = isChild;
	}

	public void setName(String name) {
		this.name = name;
	}

}
