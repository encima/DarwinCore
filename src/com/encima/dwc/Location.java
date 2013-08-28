package com.encima.dwc;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="location")
public class Location {
	
//	@Column(name="id")
	int id;
//	@Column(name="lat")
	long lat;
//	@Column(name="lng")
	long lng;
	
	String description;

	public Location(int id, long lat, long lng) {
		this.setId(id);
		this.setLat(lat);
		this.setLng(lng);
	}
	
	public Location() {
		
	}

	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getLat() {
		return lat;
	}

	public void setLat(long lat) {
		this.lat = lat;
	}

	public long getLng() {
		return lng;
	}

	public void setLng(long lng) {
		this.lng = lng;
	}

}
