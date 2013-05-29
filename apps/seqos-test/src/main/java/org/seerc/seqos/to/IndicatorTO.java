package org.seerc.seqos.to;

public class IndicatorTO {

	private String uri;
	private String label;
	private String notation;
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getNotation() {
		return notation;
	}
	public void setNotation(String notation) {
		this.notation = notation;
	}
	public IndicatorTO(String uri, String label, String notation) {
		super();
		this.uri = uri;
		this.label = label;
		this.notation = notation;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result
				+ ((notation == null) ? 0 : notation.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
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
		IndicatorTO other = (IndicatorTO) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (notation == null) {
			if (other.notation != null)
				return false;
		} else if (!notation.equals(other.notation))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "IndicatorTO [uri=" + uri + ", label=" + label + ", notation="
				+ notation + "]";
	}
	
	
}
