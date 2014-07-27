package se.aaslin.developer.finance.server.backend.util.timeframe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import se.aaslin.developer.finance.db.entity.TimeFrame;

public class TimeFrameGenerator {

	private static final SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");

	public static TimeFrame createMonthTimeFrame(Date date) {
		Date start;
		Date end;
		String tag;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		cal.set(Calendar.AM_PM, Calendar.AM);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		start = cal.getTime();

		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		cal.set(Calendar.AM_PM, Calendar.AM);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		end = cal.getTime();
		
		tag = String.format("%d %s %s", cal.get(Calendar.YEAR), getQuarter(cal), monthFormat.format(cal.getTime()));
		
		TimeFrame timeFrame = new TimeFrame();
		timeFrame.setStart(start);
		timeFrame.setStop(end);
		timeFrame.setTag(tag);
		
		return timeFrame;
	}

	private static String getQuarter(Calendar c) {
		int month = c.get(Calendar.MONTH);

		return (month >= Calendar.JANUARY && month <= Calendar.MARCH) ? "Q1" 
				: (month >= Calendar.APRIL && month <= Calendar.JUNE) ? "Q2"
				: (month >= Calendar.JULY && month <= Calendar.SEPTEMBER) ? "Q3" 
				: "Q4";
	}
}
