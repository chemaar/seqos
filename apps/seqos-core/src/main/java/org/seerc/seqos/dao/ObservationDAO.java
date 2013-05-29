package org.seerc.seqos.dao;

import java.util.List;

import org.seerc.seqos.to.ObservationTO;



public interface ObservationDAO {

	List<ObservationTO> getObservations();
	
}
