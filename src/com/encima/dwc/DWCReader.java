package com.encima.dwc;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Vector;

import org.gbif.dwc.text.Archive;
import org.gbif.dwc.text.ArchiveFactory;
import org.gbif.dwc.text.ArchiveFile;
import org.gbif.dwc.text.UnsupportedArchiveException;
import org.gbif.file.CSVReader;

import com.encima.utils.Unzipper;

public class DWCReader {
	
	String path = null;
	HibernateHelper hh;
	
	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Constructor
	 * @param path
	 */
	public DWCReader(String path) {
		this.setPath(path);
		File f = new File(path);
		System.out.println(path);
		hh = new HibernateHelper();
		if(f.exists()) {
			System.out.println("Archive loaded");
		}else{
			System.out.println("Archive not found, set path again");
			this.setPath(null);
		}
	}

	/**
	 * Read extracted zip file and send to respective Hibernate methods
	 */
	public Vector<Occurrence> extractArchive() {
		if(this.getPath() != null) {
//			ArrayList<HashMap<String, String>> darwinData = new ArrayList<HashMap<String, String>>();
			try {
				Archive arch = ArchiveFactory.openArchive(new File(this.getPath()));
				System.out.println("Reading archive from "+ arch.getLocation().getAbsolutePath());
			    
				System.out.format("Metadata Location: %s \n", arch.getMetadataLocation());
				System.out.format("Core Location: %s \n", arch.getCore().getLocation());
				
				CSVReader core = arch.getCore().getCSVReader();
				System.out.println("Reading core file....");
//				Create CSV to hold all data read from the archive
				ArrayList<HashMap<String, String>> al = readCSV(core);
				Vector<Occurrence> occs = getOccurrence(al);
				
				Iterator<ArchiveFile> extensions = arch.getExtensions().iterator();
				System.out.format("Found %d extensions: \n", arch.getExtensions().size());
				while(extensions.hasNext()) {
					ArchiveFile af = extensions.next();
					System.out.println(af.getLocation());
					ArrayList<HashMap<String, String>> ext = readCSV(af.getCSVReader());
					createExtension(af.getLocation(), ext);
				}
				return occs;
			} catch (UnsupportedArchiveException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}else{
			System.out.println("Archive could not be loaded");
			return null;
		}
		
	}
	/**
	 * Adds all extension files to Hibernate
	 * @param loc
	 * @param ext
	 */
	public void createExtension(String loc, ArrayList<HashMap<String, String>> ext) {
		if(loc.equalsIgnoreCase("images.csv")) {
			for(HashMap<String, String> data: ext) {
				ImageSet is = new ImageSet();
//				is.setId(0);
				Iterator<Entry<String, String>> hmIter = data.entrySet().iterator();
				while(hmIter.hasNext()) {
					Entry<String, String> entry = hmIter.next();
					if(entry.getKey().equalsIgnoreCase("eventID")) {
						int eventID = Integer.parseInt(entry.getValue());
						is.setEventId(eventID);
					}else if(entry.getKey().equalsIgnoreCase("identifier")) {
						is.setIdentifier(entry.getValue());
					} 
				}
				System.out.println(is.getIdentifier());
				 hh.add(is);
				
			}
		}
	}
	
	/**
	 * Reads a meta.xml file and sends to Hibernate
	 * @param darwinData
	 * @throws ParseException
	 */
	public Vector<Occurrence> getOccurrence(ArrayList<HashMap<String,String>> darwinData) throws ParseException {
		Vector<Occurrence> occs = new Vector<Occurrence>();
		for(HashMap<String, String> data: darwinData) {
			Occurrence occ = new Occurrence();
			Identification id = new Identification();
			Iterator<Entry<String, String>> hmIter = data.entrySet().iterator();
			while(hmIter.hasNext()) {
				Entry<String, String> entry = hmIter.next();
				//Occurrence Fields			
//				Matching all keys, could be stored in a properties file, with the data type to cast to
				if(entry.getKey().equalsIgnoreCase("eventID")) {
					int eventID = Integer.parseInt(entry.getValue());
					occ.setEventID(eventID);
					id.setId(eventID);
				}else if(entry.getKey().equalsIgnoreCase("eventDate")) {
					Date date = null;
					if(entry.getValue() != "") date = new SimpleDateFormat("yyyy-MM-dd").parse(entry.getValue());
					occ.setEventDate(date);
				}else if(entry.getKey().equalsIgnoreCase("eventTime")) {
					Date time = null;
					if(entry.getValue() != "") time = new SimpleDateFormat("HH:mm:ss").parse(entry.getValue());
					occ.setEventTime(time);
				}else if(entry.getKey().equalsIgnoreCase("locationID")) {
					occ.setLocationID(Integer.parseInt(entry.getValue()));
				}else if(entry.getKey().equalsIgnoreCase("recordedBy")) {
					occ.setRecordedBy(Integer.parseInt(entry.getValue()));
				}else if(entry.getKey().equalsIgnoreCase("basisOfRecord")) {
					occ.setBasisOfRecord(entry.getValue());
				}
				//Identification Fields
				if(entry.getKey().equalsIgnoreCase("identifiedBy")) {
					int idBy = -1;
					if(entry.getValue() != "") idBy = Integer.parseInt(entry.getValue());
					id.setIdentifiedBy(idBy);
				}else if(entry.getKey().equalsIgnoreCase("dateIdentified")) {
					Date date = null;
					if(entry.getValue() != "") date = new SimpleDateFormat("yyyy-MM-dd").parse(entry.getValue());
					id.setDateIdentified(date);
				}else if(entry.getKey().equalsIgnoreCase("speciesID")) {
					int speciesID = -1;
					if(entry.getValue() != "") speciesID = Integer.parseInt(entry.getValue());
					id.setSpeciesID(speciesID);
				}
			}
			occs.add(occ);
			hh.add(id);
			hh.add(occ);
		}
		return occs;
	}
	/**
	 * Reads in a csv file and returns a list of HashMaps containing the keys etc
	 * @param file
	 * @return 
	 */
	public ArrayList<HashMap<String, String>> readCSV(CSVReader file) {
		ArrayList<HashMap<String, String>> al = new ArrayList<HashMap<String,String>>();
		String[] headers = file.header;
		while(file.hasNext()) {
			HashMap<String, String> rows = new HashMap<String, String>();
			String[] row = file.next();
			for(int i = 0; i < row.length; i++) {
				rows.put(headers[i].replaceAll("\\s", ""), row[i].replaceAll("\\s", ""));
			}
			al.add(rows);
		}
		for(int i = 0; i < al.size(); i++) {
			System.out.println(al.get(i));
		}
		System.out.println("------");
		return al;
	}
		
	/**
	 * 
	 * @param args
	 * @throws IOException
	 * @throws UnsupportedArchiveException
	 */
  public static void main(String[] args) throws IOException, UnsupportedArchiveException {
	  
	  // SQLHandler sh = new SQLHandler("darwin_core", "root", "");
	  
	  //opens csv files with headers or dwc-a direcotries with a meta.xml descriptor
	  Unzipper uz = new Unzipper();
	  String path = uz.unzip(args[1]);
//	  path = "/home/encima/development/images/dwc_stuff/dwc_arch";
	  DWCReader dr = new DWCReader(path);
	  dr.extractArchive();
  }     
}