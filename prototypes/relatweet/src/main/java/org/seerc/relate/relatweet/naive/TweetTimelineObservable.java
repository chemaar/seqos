package org.seerc.relate.relatweet.naive;

import java.util.Observable;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

//FIXME: Refactor methods
public class TweetTimelineObservable extends Observable implements StatusListener  {

	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		String msg = "Got a status deletion notice id:" + statusDeletionNotice.getStatusId();
		setChanged();
		notifyObservers(msg);

	}

	public void onStallWarning(StallWarning warning) {
		String msg = "Got stall warning:" + warning;
		setChanged();
		notifyObservers(msg);

	}

	public void onStatus(Status status) {
		String msg = "@" + status.getUser().getScreenName() + " - " + status.getText();
		setChanged();
		notifyObservers(msg);

	}

	public void onException(Exception e) {
		String msg = e.getMessage();
		setChanged();
		notifyObservers(msg);

	}

	public void onScrubGeo(long userId, long upToStatusId) {		
		String msg = "Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId;
		setChanged();
		notifyObservers(msg);

	}

	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		String msg = "Got track limitation notice:" + numberOfLimitedStatuses;
		setChanged();
		notifyObservers(msg);
	}

}
