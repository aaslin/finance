package se.aaslin.developer.finance.server.backend.util.timeframe;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

public class TimeFrameTool {
	
	private static final Logger logger = Logger.getLogger(TimeFrameTool.class);
	private static final DateFormat monthFormat = new SimpleDateFormat("MMMM");
	
	public static Integer getYearFromTag(String tag) {
		String[] tagSplit = tag.split(" ");
		if (tagSplit.length != 3) {
			return null;
		}

		return Integer.parseInt(tagSplit[0]);

	}
	
	public static Integer getMonthFromTag(String tag) {
		String[] tagSplit = tag.split(" ");
		if (tagSplit.length != 3) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(monthFormat.parse(tagSplit[2]));
		} catch (ParseException e) {
			logger.error(e);
			return null;
		}
		
		return cal.get(Calendar.MONTH);
	}
}
