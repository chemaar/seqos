package org.seerc.seqos.dao;

import java.util.LinkedList;
import java.util.List;

import org.seerc.seqos.to.ObservationTO;

public class DummyObservationDAOImpl implements ObservationDAO{


	public DummyObservationDAOImpl(){
	}
	
	public List<ObservationTO> getObservations() {
		List<ObservationTO> observations = new LinkedList<ObservationTO>();
		return observations;
	}

}
