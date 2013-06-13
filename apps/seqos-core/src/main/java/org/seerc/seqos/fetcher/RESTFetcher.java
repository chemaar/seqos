package org.seerc.seqos.fetcher;



import javax.validation.Path.Node;
import javax.ws.rs.core.MediaType;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import junit.framework.Assert;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.junit.Test;
import org.seerc.seqos.to.ObservationTO;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class RESTFetcher {

	private static final String SEARCH_ENDPOINT = "http://localhost:9000/seqos-test/services/trust/{status}";

	public static void main(String []args) throws Exception{		
		RegisterBuiltin.register( ResteasyProviderFactory.getInstance() );
		ClientRequest req = new ClientRequest(SEARCH_ENDPOINT);
		req.pathParameter("status", "status");
		req.accept(MediaType.APPLICATION_XML);
		       
		ClientResponse<String> res = req.get(String.class);
		Document doc = DocumentBuilderHelper.getDocumentFromString(res.getEntity());
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expression = xpath.compile("//observation");
		NodeList nodes = (NodeList) expression.evaluate(
				  doc.getDocumentElement(), XPathConstants.NODESET);
		
		for (int i = 0 ; i<nodes.getLength();i++){
			org.w3c.dom.Node observationNode = nodes.item(i);
			ObservationTO observation = new ObservationTO();
			observation.setIndicator(xpath.evaluate("./uriDataset", observationNode));
			observation.setValue(Double.valueOf(xpath.evaluate("./value", observationNode)));
			observation.setUriProvider(xpath.evaluate("./uriProvider", observationNode));
			observation.setMd5(xpath.evaluate("./md5", observationNode));
			observation.setObservationStatus(xpath.evaluate("./observationStatus", observationNode));
			observation.setComment(xpath.evaluate("./comment", observationNode));
			observation.setLabel(xpath.evaluate("./label", observationNode));
			observation.setTimestamp(xpath.evaluate("./timeStamp", observationNode));
			System.out.println(observation);
		}
				
	}
}
