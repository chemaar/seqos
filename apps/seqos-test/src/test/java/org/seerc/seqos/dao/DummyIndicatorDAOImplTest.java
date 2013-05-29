package org.seerc.seqos.dao;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class DummyIndicatorDAOImplTest {

	@Test
	public void testLoading() {
		IndicatorDAO dao = new DummyIndicatorDAOImpl();
		Assert.assertEquals(2,dao.listIndicators().getIndicator().size());
		
	}

}
