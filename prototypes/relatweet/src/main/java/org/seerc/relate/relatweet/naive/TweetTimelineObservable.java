package org.seerc.relate.relatweet.naive;

import java.util.Observable;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;


public class TweetTimelineObservable extends Observable implements StatusListener  {

	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		String msg = "Got a status deletion notice id:" + statusDeletionNotice.getStatusId();
		sendNotification(msg);

	}

	public void onStallWarning(StallWarning warning) {
		String msg = "Got stall warning:" + warning;
		sendNotification(msg);

	}

	public void onStatus(Status status) {
		String msg = "@" + status.getUser().getScreenName() + " - " + status.getText();
		sendNotification(msg);

	}

	public void onException(Exception e) {
		String msg = e.getMessage();
		sendNotification(msg);

	}

	public void onScrubGeo(long userId, long upToStatusId) {		
		String msg = "Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId;
		sendNotification(msg);

	}

	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		String msg = "Got track limitation notice:" + numberOfLimitedStatuses;
		sendNotification(msg);
	}

	private void sendNotification(String msg) {
		setChanged();
		notifyObservers(msg);
	}
}
