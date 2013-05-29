package org.seerc.seqos.to;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "status")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "status", propOrder = {
    "observation"
})
public class ListObservationTO {

	private List<ObservationTO> observation;

	public List<ObservationTO> getObservations() {
		return observation;
	}

	public void setObservations(List<ObservationTO> observations) {
		this.observation = observations;
	}

	public ListObservationTO(List<ObservationTO> observations) {
		super();
		this.observation = observations;
	}

	public ListObservationTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((observation == null) ? 0 : observation.hashCode());
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
		ListObservationTO other = (ListObservationTO) obj;
		if (observation == null) {
			if (other.observation != null)
				return false;
		} else if (!observation.equals(other.observation))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ListObservationTO [observations=" + observation + "]";
	}


}
