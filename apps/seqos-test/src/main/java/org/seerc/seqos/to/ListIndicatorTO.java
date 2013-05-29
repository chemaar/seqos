package org.seerc.seqos.to;

import java.util.LinkedList;
import java.util.List;

public class ListIndicatorTO {
	private List<IndicatorTO> indicator;

	public List<IndicatorTO> getIndicator() {
		if(this.indicator == null){
			this.indicator = new LinkedList<IndicatorTO>();
		}
		return indicator;
	}

	public void setIndicator(List<IndicatorTO> indicator) {
		this.indicator = indicator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((indicator == null) ? 0 : indicator.hashCode());
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
		ListIndicatorTO other = (ListIndicatorTO) obj;
		if (indicator == null) {
			if (other.indicator != null)
				return false;
		} else if (!indicator.equals(other.indicator))
			return false;
		return true;
	}

	public ListIndicatorTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ListIndicatorTO(List<IndicatorTO> indicator) {
		super();
		this.indicator = indicator;
	}
	
}
