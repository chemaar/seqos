package org.seerc.seqos.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ApplicationContextLocator {

	private static final String BEANS_XML = "/resources/seqos-test-beans.xml";


	private static ApplicationContext applicationContext;

	/**
	 * Singleton (for efficiency and consistency)
	 * 
	 * @return
	 */
	public synchronized static ApplicationContext getApplicationContext() {
		if ( applicationContext == null ) {
			applicationContext = new ClassPathXmlApplicationContext(BEANS_XML);
		}
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		ApplicationContextLocator.applicationContext = applicationContext;
	}

}