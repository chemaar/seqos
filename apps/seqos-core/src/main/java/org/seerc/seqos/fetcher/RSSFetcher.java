package org.seerc.seqos.fetcher;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.seerc.seqos.exceptions.SEQOSModelException;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class RSSFetcher {
	protected URL feedURL = null;
	
	public RSSFetcher() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RSSFetcher(String feedURL) throws MalformedURLException {
		super();
		this.feedURL = new URL(feedURL);
	}

	public List fetchEntries(){
		try {
			XmlReader reader = new XmlReader(feedURL);
			SyndFeed feed = new SyndFeedInput().build(reader);
			reader.close();
			return feed.getEntries();
		} catch (IOException e) {			
			throw new SEQOSModelException("Opening stream: "+this.feedURL, e);
		} catch (IllegalArgumentException e) {
			throw new SEQOSModelException("Configuring feed RSS: "+this.feedURL,e);
		} catch (Exception e) {
			throw new SEQOSModelException("Reading feed RSS: "+this.feedURL,e);
		}
	}
	
}
