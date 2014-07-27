package se.aaslin.developer.finance.server.backend.util.timeframe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import se.aaslin.developer.finance.db.entity.TimeFrame;

public class TimeFrameGeneratorTest {

	@Test
	public void testCreateMonthTimeFrame() throws ParseException {
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2012-06-02");
		TimeFrame timeFrame = TimeFrameGenerator.createMonthTimeFrame(date);
		Assert.assertEquals("2012 Q2 June", timeFrame.getTag());
		Assert.assertEquals(new SimpleDateFormat("yyyy-MM-dd").parseObject("2012-06-01"), timeFrame.getStart());
		Assert.assertEquals(new SimpleDateFormat("yyyy-MM-dd").parseObject("2012-06-30"), timeFrame.getStop());

		date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2010-02-15 23:59:59.000");
		timeFrame = TimeFrameGenerator.createMonthTimeFrame(date);
		Assert.assertEquals("2010 Q1 February", timeFrame.getTag());
		Assert.assertEquals(new SimpleDateFormat("yyyy-MM-dd").parseObject("2010-02-01"), timeFrame.getStart());
		Assert.assertEquals(new SimpleDateFormat("yyyy-MM-dd").parseObject("2010-02-28"), timeFrame.getStop());
	}
}
