package org.seerc.seqos.to;


import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "ObservationTO")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObservationTO", propOrder = {
    "uriDataset",
    "measure",
    "value",
    "dimensions",
    "uriProvider",
    "md5",
    "observationStatus",
    "comment",
    "label",
    "timeStamp"
})
public class ObservationTO {


	/**The URI of the target dataset.**/
	String uriDataset; 
	/**The URI of the measure that is being aggregated.**/
	String measure;
	/**The value of the measure that is being generated.**/
	String value;
	Map dimensions = new HashMap<String, String>();
	
	String uriProvider = "http://purl.org/seerc/seqos/organization/test";
	String md5;
	String observationStatus;
	String comment;
	String label;	
	long timeStamp;
	
	public String getUriDataset() {
		return uriDataset;
	}
	public void setUriDataset(String uriDataset) {
		this.uriDataset = uriDataset;
	}
	public String getMeasure() {
		return measure;
	}
	public void setMeasure(String measure) {
		this.measure = measure;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Map getDimensions() {
		return dimensions;
	}
	public void setDimensions(Map dimensions) {
		this.dimensions = dimensions;
	}
	public String getUriProvider() {
		return uriProvider;
	}
	public void setUriProvider(String uriProvider) {
		this.uriProvider = uriProvider;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getObservationStatus() {
		return observationStatus;
	}
	public void setObservationStatus(String observationStatus) {
		this.observationStatus = observationStatus;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public ObservationTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ObservationTO [uriDataset=" + uriDataset + ", measure="
				+ measure + ", value=" + value + ", dimensions=" + dimensions
				+ ", uriProvider=" + uriProvider + ", md5=" + md5
				+ ", observationStatus=" + observationStatus + ", comment="
				+ comment + ", label=" + label + ", timeStamp=" + timeStamp
				+ "]";
	}
	
	
	



}
