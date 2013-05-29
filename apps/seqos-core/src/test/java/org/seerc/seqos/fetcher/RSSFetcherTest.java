package org.seerc.seqos.fetcher;


import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Test;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class RSSFetcherTest {
	
	public void test() throws IOException, IllegalArgumentException, FeedException {
		URL url  = new URL("http://trust.salesforce.com/rest/rss/NA11");
		XmlReader reader = null;
		try {
			reader = new XmlReader(url);
			SyndFeed feed = new SyndFeedInput().build(reader);
			System.out.println("Feed Title: "+ feed.getAuthor());
			for (Iterator i = feed.getEntries().iterator(); i.hasNext();) {
				SyndEntry entry = (SyndEntry) i.next();
				System.out.println(entry.getTitle());
			}
		} finally {
			if (reader != null)
				reader.close();
		}
	}
	
	@Test
	public void testLocal() throws IOException, IllegalArgumentException, FeedException{
		XmlReader reader = 
				new XmlReader(Thread.currentThread().getContextClassLoader().
						getResourceAsStream("trust-sales-force.xml"));
		SyndFeed feed = new SyndFeedInput().build(reader);
		Assert.assertEquals(3,feed.getEntries().size());
	}
	
	@Test
	public void testWithClass() throws IOException, IllegalArgumentException, FeedException{
		RSSFetcher fetcher = new RSSFetcher("http://trust.salesforce.com/rest/rss/NA11");
		Assert.assertEquals(3,fetcher.fetchEntries().size());
	}
	
	@Test
	public void testLocalClass() throws IOException, IllegalArgumentException, FeedException{
		RSSFetcher fetcher = new LocalRSSFetcher("trust-sales-force.xml");
		Assert.assertEquals(3,fetcher.fetchEntries().size());
	}
}


