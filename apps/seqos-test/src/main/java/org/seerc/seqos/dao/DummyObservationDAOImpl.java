package org.seerc.seqos.dao;

import java.util.LinkedList;
import java.util.List;

import org.seerc.seqos.to.ListObservationTO;
import org.seerc.seqos.to.ObservationTO;
import org.seerc.seqos.utils.MD5Utils;
import org.seerc.seqos.utils.StatusUtils;

public class DummyObservationDAOImpl implements ObservationDAO{

	private static final String HTTP_PURL_ORG_SEERC_SEQOS_INDICATOR_SERVICE_RESPONSE_TIME = "http://purl.org/seerc/seqos/indicator/ServiceResponseTime";
	private static final int MAX = 10;

	public ListObservationTO getObservations() {
		List<ObservationTO> observations = new LinkedList<ObservationTO>();
		for(int i = 0; i<MAX; i++){
			//FIXME: select indicator from a list
			observations.add(createObservationTO(
					HTTP_PURL_ORG_SEERC_SEQOS_INDICATOR_SERVICE_RESPONSE_TIME,
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
