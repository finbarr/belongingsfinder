package com.belongingsfinder.api.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;

import com.sun.istack.internal.NotNull;

@Entity
@Indexed
public class BelongingModel implements Model<BelongingModel>, Serializable {

	private static final long serialVersionUID = 6587819556855066435L;

	@Id
	private String id;
	private String imageUrl;
	@Field(index = Index.TOKENIZED, store = Store.NO)
	@Lob
	private String description;
	// TODO location search
	@Embedded
	private LatLon location;
	@Field(index = Index.UN_TOKENIZED, store = Store.NO)
	@Enumerated(EnumType.STRING)
	@NotNull
	private Type type;
	@NotNull
	private String email;
	@NotNull
	@IndexedEmbedded
	@ManyToOne(fetch = FetchType.EAGER)
	private CategoryModel category;
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateBridge(resolution = Resolution.DAY)
	private Date lastUpdated;

	public CategoryModel getCategory() {
		return category;
	}

	public String getDescription() {
		return description;
	}

	public String getEmail() {
		return email;
	}

	public String getId() {
		return id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	@JsonIgnore
	public Date getLastUpdated() {
		return lastUpdated;
	}

	public LatLon getLocation() {
		return location;
	}

	public Type getType() {
		return type;
	}

	public void setCategory(CategoryModel category) {
		this.category = category;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@JsonIgnore
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setLocation(LatLon location) {
		this.location = location;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public enum Type {

		LOST("lost"), FOUND("found"), ALL("all");

		private final String name;

		private Type(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

	}

}
