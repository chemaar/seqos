package org.seerc.seqos.dao;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import org.seerc.seqos.to.IndicatorTO;
import org.seerc.seqos.to.ListIndicatorTO;

public class DummyIndicatorDAOImpl implements IndicatorDAO {
	static ListIndicatorTO indicator = null; 

	static{
		indicator = load(ResourceBundle.getBundle(DummyIndicatorDAOImpl.class.getName().toString()));
	}

	private static ListIndicatorTO load(ResourceBundle bundle) {
		List<IndicatorTO> loadedIndicators = new LinkedList<IndicatorTO>();
		Enumeration<String> keys = bundle.getKeys();		
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			String uri = bundle.getString(key);
			loadedIndicators.add(new IndicatorTO(uri, key, key));			
		}
		return new ListIndicatorTO(loadedIndicators);
	}
	


	public ListIndicatorTO listIndicators() {		
		return indicator;
	}
}

