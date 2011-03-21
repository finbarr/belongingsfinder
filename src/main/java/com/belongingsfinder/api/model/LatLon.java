package com.belongingsfinder.api.model;

import java.io.Serializable;
import java.util.StringTokenizer;

import javax.persistence.Embeddable;

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
	private double lat;
	@Field(index = Index.UN_TOKENIZED)
	@NumericField
	private double lon;

	public LatLon() {
	}

	public LatLon(double lat, double lon) {
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

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return lat + "," + lon;
	}

}
