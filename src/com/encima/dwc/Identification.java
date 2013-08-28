/**
 * 
 */
package com.encima.dwc;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author encima
 *
 */
@Entity
@Table(name="identification")
public class Identification {
	
//	@Column(name="id")
	int id;
//	@Column(name="identifiedBy")
	int identifiedBy;
//	@Column(name="dateIdentified")
	Date dateIdentified;
//	@Column(name="speciesID")
	int speciesID;
	
	public Identification(int id, int identifiedBy, Date dateIdentified, int speciesID) {
		this.setId(id);
		this.setIdentifiedBy(identifiedBy);
		this.setDateIdentified(dateIdentified);
		this.setSpeciesID(speciesID);
	}
	
	public Identification() {
		
	}

	@Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdentifiedBy() {
		return identifiedBy;
	}
	public void setIdentifiedBy(int identifiedBy) {
		this.identifiedBy = identifiedBy;
	}
	public Date getDateIdentified() {
		return dateIdentified;
	}
	public void setDateIdentified(Date dateidentified) {
		this.dateIdentified = dateidentified;
	}
	public int getSpeciesID() {
		return speciesID;
	}
	public void setSpeciesID(int speciesID) {
		this.speciesID = speciesID;
	}
}
