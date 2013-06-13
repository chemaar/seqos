package org.seerc.seqos.fetcher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * This is a helper class for building Documents from different sources.
 */
public class DocumentBuilderHelper{
    
    private static final Logger logger = Logger.getLogger(DocumentBuilderHelper.class); 
    
	public static Reader getStringReader(String article){
		return new StringReader(article);
	}
	
	public static InputSource getInputSourceFromString(String article){
		Reader reader = getStringReader(article);
		return new InputSource(reader);
	}
	
	public static InputSource getInputSourceFromReader(Reader article){       
		return new InputSource(article);
	}

	public static DocumentBuilder createDocumentBuilder() throws DocumentBuilderException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		try {
			return factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new DocumentBuilderException(e);
		}
	}
	
	public static Document getDocumentFromString(String input) throws DocumentBuilderException {
		try {
			return createDocumentBuilder().parse(getInputSourceFromString(input));
		} catch (SAXException e) {
			throw new DocumentBuilderException(e);
		} catch (IOException e) {
			throw new DocumentBuilderException(e);
		} catch (DocumentBuilderException e) {
			throw new DocumentBuilderException(e);
		}
	}
	
	public static Document getDocumentFromReader(Reader input) throws DocumentBuilderException {
		try {
			return createDocumentBuilder().parse(getInputSourceFromReader(input));
		} catch (SAXException e) {
			throw new DocumentBuilderException(e);
		} catch (IOException e) {
			throw new DocumentBuilderException(e);
		}
	}
	
	public static Document getDocumentFromInputStream(InputStream input) throws DocumentBuilderException {
		try {
			return createDocumentBuilder().parse(input);
		} catch (SAXException e) {
			throw new DocumentBuilderException(e);
		} catch (IOException e) {
			throw new DocumentBuilderException(e);
		}
	}
	
	public static Document getEmptyDocument() throws DocumentBuilderException {
		return createDocumentBuilder().newDocument();
	}

    public static Document getDocumentFromFile(File file) throws DocumentBuilderException, FileNotFoundException {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            return getDocumentFromInputStream(is);            
        } finally {
            try {
                if (is != null) { is.close(); }
            } catch (IOException e) {
                logger.error("Failed to close file, resuming work", e);
            }
        }
    }
    
}

