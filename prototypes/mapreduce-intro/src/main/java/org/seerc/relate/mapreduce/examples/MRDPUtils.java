package org.seerc.relate.mapreduce.examples;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;

public class MRDPUtils {

	public static final String CREATION_DATE = "CreationDate";
	public static final String USER_ID = "UserId";
	public static final String TEXT = "Text";
	public final static SimpleDateFormat frmt = new SimpleDateFormat(
			"yyyy-MM-dd' 'HH:mm:ss");
	
	public static Map<String, String> parse(String string) {
		String [] values = string.split(",");
		Map<String, String> fields = new HashMap<String,String>(3);
		System.out.println(string);
		fields.put(USER_ID, values[0]);
		fields.put(CREATION_DATE, values[1]);
		String text = "";
		for(int i = 2; i<values.length;i++){
			text += values[i];
		}
		fields.put(TEXT, text);
		return fields;
	}

}
