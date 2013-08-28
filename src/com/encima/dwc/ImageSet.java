package com.encima.dwc;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="imageset")
public class ImageSet {

	@Id
	@GeneratedValue
	int id;
	int eventId;
	String identifier;
	
	public ImageSet() {
	}
	
	public ImageSet(int id, int eventId, String identifier) {
		this.id = id;
		this.eventId = eventId;
		this.identifier = identifier;
	}
	
	public ImageSet(int eventId, String identifier) {
		this.eventId = eventId;
		this.identifier = identifier;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEventId() {
		return id;
	}
	public void setEventId(int id) {
		this.id = id;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	
}
