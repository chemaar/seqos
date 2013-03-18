package org.seerc.relate.relatweet.lambda.speed.workers;

import java.io.IOException;


public class NaiveTwitterRealTimeMain {
	public static void main(String []args) throws IOException, InterruptedException{
		NaiveTweetTimelineObservable observable = new NaiveTweetTimelineObservable();
		NaiveTweetTimelineObserver observer = new NaiveTweetTimelineObserver();
		observable.addObserver(observer);
		observable.execute();
	}
}
