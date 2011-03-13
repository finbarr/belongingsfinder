package com.belongingsfinder.api.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
public class BelongingModel implements Model<BelongingModel>, Serializable {

	private static final long serialVersionUID = 6587819556855066435L;

	@Id
	private String id;
	private String imageUrl;
	private String description;
	@Embedded
	private LatLon location;
	@Enumerated(EnumType.STRING)
	private Type type;
	private String email;
	private String categoryId;
	private Date lastUpdated;

	public String getCategoryId() {
		return categoryId;
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

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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
