package org.seerc.relate.relatweet.naive;

public class NaiveTweetTimelineMain {


	public static void main(String[] args) throws InterruptedException {
		TweetTimelineObservable observable = new TweetTimelineObservable();
		TweetTimelineObserver observer = new TweetTimelineObserver();
		observable.addObserver(observer);
		observable.execute();		
	}

}
