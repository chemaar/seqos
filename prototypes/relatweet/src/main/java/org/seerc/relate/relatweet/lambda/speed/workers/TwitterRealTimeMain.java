package org.seerc.relate.relatweet.lambda.speed.workers;

import java.io.IOException;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;


public class TwitterRealTimeMain {
	public static void main(String []args) throws IOException, InterruptedException{
		TweetTimelineObservable observable = new TweetTimelineObservable();
		TweetTimelineObserver observer = new TweetTimelineObserver();
		observable.addObserver(observer);
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(observable);
		twitterStream.sample();
	}
}
