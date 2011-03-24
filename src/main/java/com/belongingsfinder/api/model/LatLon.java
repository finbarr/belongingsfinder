package com.belongingsfinder.api.model;

import java.io.Serializable;
import java.util.StringTokenizer;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.NumericField;

/**
 * @author Finbarr
 * 
 */
@Embeddable
public class LatLon implements Serializable {

	private static final long serialVersionUID = -4728508792442337147L;

	@Field(index = Index.UN_TOKENIZED)
	@NumericField
	@NotNull
	private Double lat;
	@Field(index = Index.UN_TOKENIZED)
	@NumericField
	@NotNull
	private Double lon;

	public LatLon() {
	}

	public LatLon(Double lat, Double lon) {
		this.lat = lat;
		this.lon = lon;
	}

	public LatLon(String both) {
		StringTokenizer st = new StringTokenizer(both, ", ");
		lat = Double.parseDouble(st.nextToken());
		lon = Double.parseDouble(st.nextToken());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LatLon) {
			LatLon other = (LatLon) obj;
			return other.hashCode() == hashCode();
		}
		return false;
	}

	public Double getLat() {
		return lat;
	}

	public Double getLon() {
		return lon;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return new StringBuilder(lat.toString()).append(",").append(lon.toString()).toString();
	}

}
