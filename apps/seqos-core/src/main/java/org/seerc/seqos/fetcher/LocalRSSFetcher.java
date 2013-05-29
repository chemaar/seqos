package org.seerc.seqos.fetcher;


public class LocalRSSFetcher extends RSSFetcher{
	
	public LocalRSSFetcher(String feedURL) {
		super();
		this.feedURL = Thread.currentThread().getContextClassLoader().getResource(feedURL);
	}
}
