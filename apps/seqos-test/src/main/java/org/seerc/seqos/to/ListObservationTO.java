package org.seerc.seqos.to;

import java.util.List;

public class ListObservationTO {

	private List<ObservationTO> observations;

	public List<ObservationTO> getObservations() {
		return observations;
	}

	public void setObservations(List<ObservationTO> observations) {
		this.observations = observations;
	}

	public ListObservationTO(List<ObservationTO> observations) {
		super();
		this.observations = observations;
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
				+ ((observations == null) ? 0 : observations.hashCode());
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
		if (observations == null) {
			if (other.observations != null)
				return false;
		} else if (!observations.equals(other.observations))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ListObservationTO [observations=" + observations + "]";
	}


}
