package com.belongingsfinder.api.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

import com.sun.istack.internal.NotNull;

/**
 * @author finbarr
 * 
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class CategoryModel implements Model<CategoryModel>, Serializable {

	private static final long serialVersionUID = -4520507504770029807L;

	@Id
	@GeneratedValue(generator = "bf-uuid")
	@GenericGenerator(name = "bf-uuid", strategy = "com.belongingsfinder.api.dao.jpa.UUIDIdentifierGenerator")
	@Field(index = Index.UN_TOKENIZED)
	private String id;
	@NotNull
	@NotEmpty
	private String name;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", fetch = FetchType.EAGER)
	@Valid
	private Set<CategoryModel> children;
	@ManyToOne
	// @Valid would this cause infinite recursion?
	private CategoryModel parent;

	public Set<CategoryModel> getChildren() {
		return children;
	}

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

	public void setChildren(Set<CategoryModel> children) {
		this.children = children;
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
