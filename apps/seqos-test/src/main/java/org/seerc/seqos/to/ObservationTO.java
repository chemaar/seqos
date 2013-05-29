package org.seerc.seqos.to;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "ObservationTO")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObservationTO", propOrder = {
    "label",
    "comment",
    "timestamp",
    "md5",
    "uriProvider",
    "observationStatus",
    "indicator",
    "value"
})
public class ObservationTO {

	private String label;
	private String comment;
	private long timestamp;
	private String md5 = "";
	private String uriProvider;
	private String observationStatus;
	private String indicator;
	private double value;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getUriProvider() {
		return uriProvider;
	}
	public void setUriProvider(String uriProvider) {
		this.uriProvider = uriProvider;
	}
	public String getObservationStatus() {
		return observationStatus;
	}
	public void setObservationStatus(String observationStatus) {
		this.observationStatus = observationStatus;
	}
	public String getIndicator() {
		return indicator;
	}
	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result
				+ ((indicator == null) ? 0 : indicator.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((md5 == null) ? 0 : md5.hashCode());
		result = prime
				* result
				+ ((observationStatus == null) ? 0 : observationStatus
						.hashCode());
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		result = prime * result
				+ ((uriProvider == null) ? 0 : uriProvider.hashCode());
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObservationTO other = (ObservationTO) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (indicator == null) {
			if (other.indicator != null)
				return false;
		} else if (!indicator.equals(other.indicator))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (md5 == null) {
			if (other.md5 != null)
				return false;
		} else if (!md5.equals(other.md5))
			return false;
		if (observationStatus == null) {
			if (other.observationStatus != null)
				return false;
		} else if (!observationStatus.equals(other.observationStatus))
			return false;
		if (timestamp != other.timestamp)
			return false;
		if (uriProvider == null) {
			if (other.uriProvider != null)
				return false;
		} else if (!uriProvider.equals(other.uriProvider))
			return false;
		if (Double.doubleToLongBits(value) != Double
				.doubleToLongBits(other.value))
			return false;
		return true;
	}
	public ObservationTO(String label, String comment, long timestamp,
			String md5, String uriProvider, String observationStatus,
			String indicator, double value) {
		super();
		this.label = label;
		this.comment = comment;
		this.timestamp = timestamp;
		this.md5 = md5;
		this.uriProvider = uriProvider;
		this.observationStatus = observationStatus;
		this.indicator = indicator;
		this.value = value;
	}
	@Override
	public String toString() {
		return "ObservationTO [label=" + label + ", comment=" + comment
				+ ", timestamp=" + timestamp + ", md5=" + md5
				+ ", uriProvider=" + uriProvider + ", observationStatus="
				+ observationStatus + ", indicator=" + indicator + ", value="
				+ value + "]";
	}
	public ObservationTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
