package org.seerc.seqos.business;

import org.seerc.seqos.dao.ObservationDAO;
import org.seerc.seqos.to.ListObservationTO;
import org.seerc.seqos.utils.ApplicationContextLocator;

public class StatusBusinessImpl implements StatusBusiness{

	//FIXME: it should be Application Service not direct access to the DAO objects
	ObservationDAO dao;
	
	public StatusBusinessImpl(){
		this.dao = (ObservationDAO) ApplicationContextLocator.getApplicationContext().getBean(ObservationDAO.class.getSimpleName());
	}
	
	public ListObservationTO status() {
		return dao.getObservations();
	}
}
