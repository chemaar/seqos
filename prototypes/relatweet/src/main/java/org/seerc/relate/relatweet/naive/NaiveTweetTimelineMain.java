package org.seerc.relate.relatweet.naive;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class NaiveTweetTimelineMain {


	public static void main(String[] args) throws InterruptedException {
		TweetTimelineObservable observable = new TweetTimelineObservable();
		TweetTimelineObserver observer = new TweetTimelineObserver();
		observable.addObserver(observer);
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(observable);
		twitterStream.sample();
	}

}
