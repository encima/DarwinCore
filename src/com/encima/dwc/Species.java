package com.encima.dwc;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="species")
public class Species {
	
//	@Column(name="id")
	int id;
//	@Column(name="name")
	String name;
//	@Column(name="scientificName")
	String scientificName;
	
	public Species(int id, String name, String scientificName) {
		this.setId(id);
		this.setName(name);
		this.setScientificName(scientificName);
	}
	
	public Species() {
		
	}
	
	@Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScientificName() {
		return scientificName;
	}
	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}
}
