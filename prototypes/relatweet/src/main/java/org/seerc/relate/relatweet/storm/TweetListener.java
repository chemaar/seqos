package org.seerc.relate.relatweet.storm;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class TweetListener implements StatusListener{

	private static final int MAX_STATUS = 10;
	TweetSpout spout;
	int counter = 0;
	
	public TweetListener(TweetSpout tweetSpout) {
		this.spout = tweetSpout;
	}

	public void onException(Exception arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onDeletionNotice(StatusDeletionNotice arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onScrubGeo(long arg0, long arg1) {
		// TODO Auto-generated method stub
		
	}

	public void onStallWarning(StallWarning arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onStatus(Status status) {		
		if(this.counter == MAX_STATUS){
			//Finalize
		}else{
			this.spout.getQueue().offer(status.getText());
			this.counter++;
		}
		
		
	}

	public void onTrackLimitationNotice(int arg0) {
		// TODO Auto-generated method stub
		
	}

}
