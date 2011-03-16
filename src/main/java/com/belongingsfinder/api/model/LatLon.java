package com.belongingsfinder.api.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

/**
 * @author Finbarr
 * 
 */
@Embeddable
public class LatLon implements Serializable {

	private static final long serialVersionUID = -4728508792442337147L;
	@Field(index = Index.UN_TOKENIZED, store = Store.NO)
	private String lat;
	@Field(index = Index.UN_TOKENIZED, store = Store.NO)
	private String lon;

	public LatLon() {
	}

	public LatLon(String both) {
		String[] parts = both.split(",");
		lat = parts[0].trim();
		lon = parts[1].trim();
	}

	public LatLon(String lat, String lon) {
		this.lat = lat;
		this.lon = lon;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LatLon) {
			LatLon other = (LatLon) obj;
			return other.hashCode() == hashCode();
		}
		return false;
	}

	public String getLat() {
		return lat;
	}

	public String getLon() {
		return lon;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return lat + "," + lon;
	}

}
