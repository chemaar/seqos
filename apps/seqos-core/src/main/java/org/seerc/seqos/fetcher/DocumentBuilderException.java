package org.seerc.seqos.fetcher;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * This class models an exception occured during the creation of a DOM.
 */
public class DocumentBuilderException extends Exception {

    public DocumentBuilderException(DocumentBuilderException e) {
        super(e);
    }

    public DocumentBuilderException(IOException e) {
        super(e);
    }

    public DocumentBuilderException(ParserConfigurationException e) {
        super(e);
    }

    public DocumentBuilderException(SAXException e) {
        super(e);
    }

}
