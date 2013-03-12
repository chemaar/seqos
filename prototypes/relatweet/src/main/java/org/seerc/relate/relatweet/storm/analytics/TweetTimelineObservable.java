package org.seerc.relate.relatweet.storm.analytics;

import java.util.Observable;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;


public class TweetTimelineObservable extends Observable implements StatusListener  {

	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
	
	}

	public void onStallWarning(StallWarning warning) {
	
	}

	public void onStatus(Status status) {
		String msg = "@" + status.getUser().getScreenName() + " - " + status.getText();
		sendNotification(msg);

	}

	public void onException(Exception e) {
	
	}

	public void onScrubGeo(long userId, long upToStatusId) {		
	
	}

	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
	}

	private void sendNotification(String msg) {
		try {
			setChanged();
			notifyObservers(msg);
			Thread.currentThread().sleep(500); //FIXME
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
