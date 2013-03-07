package org.seerc.relate.relatweet.naive;

import java.util.Observable;

public class TweetTimelineObservable extends Observable {

	public void execute() throws InterruptedException{
		while(true){
			  setChanged();
		      notifyObservers(System.nanoTime());
		      Thread.currentThread().sleep(3000);
		}
	}
	 
}
