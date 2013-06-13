package org.seerc.seqos.dao;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;

import javax.management.RuntimeErrorException;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

public class LocalSPARQLDataSource {
	private String fileName = "seqos-core.ttl";
	private String defaultGraphName;
	private String previousDescribeQuery;
	private String endpointURL;
	private Model model;
	private String format = "TURTLE";
	
	public LocalSPARQLDataSource(String endpointURL, String defaultGraphName) {
		this.endpointURL = endpointURL;
		this.defaultGraphName = defaultGraphName;
	}
	
	public String getEndpointURL() {
		return this.fileName;
	}
	
	public String getResourceDescriptionURL(String resourceURI) {
		try {
			StringBuffer result = new StringBuffer();
			result.append(endpointURL);
			result.append("?");
			if (defaultGraphName != null) {
				result.append("default-graph-uri=");
				result.append(URLEncoder.encode(defaultGraphName, "utf-8"));
				result.append("&");
			}
			result.append("query=");
			result.append(URLEncoder.encode("DESCRIBE <" + resourceURI + ">", "utf-8"));
			return result.toString();
		} catch (UnsupportedEncodingException ex) {
			// can't happen, utf-8 is always supported
			throw new RuntimeException(ex);
		}
	}
	
	public Model getResourceDescription(String resourceURI) {
		return execDescribeQuery("DESCRIBE <" + resourceURI + ">");
	}
	
	public Model getAnonymousPropertyValues(String resourceURI, Property property, boolean isInverse) {
		String query = "DESCRIBE ?x WHERE { "
			+ (isInverse 
					? "?x <" + property.getURI() + "> <" + resourceURI + "> . "
					: "<" + resourceURI + "> <" + property.getURI() + "> ?x . ")
			+ "FILTER (isBlank(?x)) }";
		return execDescribeQuery(query);
	}
	
	public String getPreviousDescribeQuery() {
		return previousDescribeQuery;
	}
	
	private Model execDescribeQuery(String query) {
		previousDescribeQuery = query;
		//QueryEngineHTTP endpoint = new QueryEngineHTTP(endpointURL, query);
	    QueryExecution qExec = QueryExecutionFactory.create(query, model);
		if (defaultGraphName != null) {
			//endpoint.setDefaultGraphURIs(Collections.singletonList(defaultGraphName));
		}
		return qExec.execDescribe();
	}
	
  
  public Model createModel(String filename) {
	  this.model =ModelFactory.createDefaultModel();
	  try {
		this.model.read(createInputStream(filename), "",this.format);
	} catch (FileNotFoundException e) {
		throw new RuntimeErrorException(new Error(e));
	}
	return model; 
  }
  
  private InputStream createInputStream (String filename) throws FileNotFoundException {
	  ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	  InputStream in = classLoader.getResourceAsStream(filename);
	  if (in == null) {
	      throw new FileNotFoundException(filename);
	  } else {
	      return in;
	  }
}


  
  
}
