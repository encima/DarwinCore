package com.encima.dwc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="occurrence")
public class Occurrence {
	
//	@Column(name="eventID")
	int eventID;
//	@Column(name="eventDate")
	Date eventDate;
//	@Column(name="eventDate")
	Date eventTime;
//	@Column(name="locationID")
	int locationID;
//	@Column(name="basisOfRecord")
	String basisOfRecord;
//	@Column(name="recordedBy")
	int recordedBy;
	
	public Occurrence() {
		
	}
	
	public Occurrence(int eventID, Date eventDate, Date eventTime, int locationID, String basisOfRecord, int recordedBy) {
		this.setEventID(eventID);
		this.setEventDate(eventDate);
		this.setEventTime(eventTime);
		this.setLocationID(locationID);
		this.setBasisOfRecord(basisOfRecord);
		this.setRecordedBy(recordedBy);
	}
	
	public Occurrence(String eventID, String eventDate, String eventTime, String locationID, String basisOfRecord, String recordedBy) throws ParseException {
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(eventDate);
		Date time = new SimpleDateFormat("HH:mm:ss").parse(eventTime);
		this.setEventID(Integer.parseInt(eventID));
		this.setEventDate(date);
		this.setEventTime(time);
		this.setLocationID(Integer.parseInt(locationID));
		this.setBasisOfRecord(basisOfRecord);
		this.setRecordedBy(Integer.parseInt(recordedBy));
	}
	
	@Id
	public int getEventID() {
		return eventID;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public Date getEventTime() {
		return eventTime;
	}
	public void setEventTime(Date date) {
		this.eventTime = date;
	}
	public int getLocationID() {
		return locationID;
	}
	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}
	public String getBasisOfRecord() {
		return basisOfRecord;
	}
	public void setBasisOfRecord(String basisOfRecord) {
		this.basisOfRecord = basisOfRecord;
	}
	public int getRecordedBy() {
		return recordedBy;
	}
	public void setRecordedBy(int recordedBy) {
		this.recordedBy = recordedBy;
	}	
}
