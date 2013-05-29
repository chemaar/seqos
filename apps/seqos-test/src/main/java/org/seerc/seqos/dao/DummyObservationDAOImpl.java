package org.seerc.seqos.dao;

import java.util.LinkedList;
import java.util.List;

import org.seerc.seqos.to.IndicatorTO;
import org.seerc.seqos.to.ListObservationTO;
import org.seerc.seqos.to.ObservationTO;
import org.seerc.seqos.utils.ApplicationContextLocator;
import org.seerc.seqos.utils.MD5Utils;
import org.seerc.seqos.utils.StatusUtils;

public class DummyObservationDAOImpl implements ObservationDAO{

	private IndicatorDAO indicatorDAO;

	public DummyObservationDAOImpl(){
		this.indicatorDAO = (IndicatorDAO) ApplicationContextLocator.getApplicationContext().getBean(IndicatorDAO.class.getSimpleName());
	}
	
	public DummyObservationDAOImpl(IndicatorDAO indicatorDAO){
		this.indicatorDAO = indicatorDAO;
	}
	
	
	
	public ListObservationTO getObservations() {
		List<ObservationTO> observations = new LinkedList<ObservationTO>();
		List<IndicatorTO> indicators = this.indicatorDAO.listIndicators().getIndicator();
		for(IndicatorTO indicator:indicators){
			observations.add(createObservationTO(
					indicator.getUri(),
					StatusUtils.NORMAL));
		}		
		return new ListObservationTO(observations);		
	}
	
	private ObservationTO createObservationTO(String indicator, StatusUtils normal){
		ObservationTO observation = new ObservationTO();
		observation.setComment("Auto-generated comment");
		observation.setLabel("Auto-generated label");
		observation.setIndicator(indicator);
		observation.setTimestamp(System.nanoTime());
		observation.setMd5(MD5Utils.generate(observation.toString()));
		observation.setValue(generateRandomValue());
		observation.setObservationStatus(normal.name());
		return observation;
	}

	private double generateRandomValue() {
		return Math.random();
	}

}
