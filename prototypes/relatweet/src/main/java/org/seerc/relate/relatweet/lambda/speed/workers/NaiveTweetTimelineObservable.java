package org.seerc.relate.relatweet.lambda.speed.workers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import org.apache.log4j.Logger;


public class NaiveTweetTimelineObservable extends Observable {

	protected static Logger logger = Logger.getLogger(NaiveTweetTimelineObservable.class);
	List<String> messages;
	Random rand = new Random();
	public NaiveTweetTimelineObservable(){
		this.messages= load();
	}

	public  void execute() {
		int size = this.messages.size()-1;
		while (true){
			sendNotification(this.messages.get(rand.nextInt(size)));
		}
		
	}

	private List<String> load() {
		List<String> lines = new LinkedList<String>();
		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("test/dump-tweets.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			line = br.readLine();
			while(line!=null){
				lines.add(line);
				line = br.readLine();
			}
			is.close();
			br.close();
		} catch (IOException e) {
			logger.error(e);
		}
		return lines;
	}

	private void sendNotification(String msg) {
		try {
			setChanged();
			notifyObservers(msg);
			Thread.currentThread().sleep(500); //FIXME
		} catch (InterruptedException e) {
			logger.error(e);
		}
	}
}
