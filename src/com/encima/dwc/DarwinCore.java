package com.encima.dwc;

import java.util.Vector;

public class DarwinCore {

	Occurrence occ;
	Identification id;
	Vector<ImageSet> is;
	
	public DarwinCore(Occurrence occ, Identification id, Vector<ImageSet> is) {
		super();
		this.occ = occ;
		this.id = id;
		this.is = new Vector<ImageSet>(is);
	}

	public Occurrence getOcc() {
		return occ;
	}

	public void setOcc(Occurrence occ) {
		this.occ = occ;
	}

	public Identification getId() {
		return id;
	}

	public void setId(Identification id) {
		this.id = id;
	}

	public Vector<ImageSet> getIs() {
		return is;
	}

	public void setIs(Vector<ImageSet> is) {
		this.is = new Vector<ImageSet>(is);
	}
}
