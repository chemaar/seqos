import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.apache.hadoop.io.NullWritable;
import org.junit.Test;


public class TestParsingDate {

	@Test
	public void testParsingDate() throws ParseException{
		SimpleDateFormat frmt = new SimpleDateFormat(
				"yyyy-MM-dd' 'HH:mm:ss");
		frmt.parse("2013-04-08 12:55:45");
	}
	@Test
	public void testMatchGrep() throws ParseException{
		String txt = "RT @DavidAllenGreen: One irony for the liberal left is that most important modern statute for civil liberties in practical terms - PACE  ...";
		String patternString = ".*\\biron(.)*\\b.*";
		Pattern p = Pattern.compile(patternString);
		System.out.println(p.matcher(txt));
		Assert.assertTrue(txt.matches(patternString));
		
	}

	
}
