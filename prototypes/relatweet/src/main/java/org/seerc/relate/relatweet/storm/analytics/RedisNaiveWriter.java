package org.seerc.relate.relatweet.storm.analytics;

import redis.clients.jedis.Jedis;

public class RedisNaiveWriter {

	public static void main(String []args){
		Jedis jedis;
		String host = "localhost"; 
		int port = 6379;
		jedis = new Jedis(host,port);
		jedis.set("foo", "bar");
		String value = jedis.get("foo");
		System.out.println(value);
	}
}
