package se.aaslin.developer.finance.server.backend.util.timeframe;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import se.aaslin.developer.finance.server.backend.util.timeframe.TimeFrameTool;

public class TimeFrameToolTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testGetYearFromTag() {
		int year = TimeFrameTool.getYearFromTag("2012 Q1 January");
		Assert.assertEquals(2012, year);
		year = TimeFrameTool.getYearFromTag("2011 Q3 September");
		Assert.assertEquals(2011, year);
		
		exception.expect(NullPointerException.class);
		year = TimeFrameTool.getYearFromTag("2013 January");
	}
	
	@Test
	public void testGetMonthFromTag() {
		int month = TimeFrameTool.getMonthFromTag("2012 Q1 January");
		Assert.assertEquals(0, month);
		month = TimeFrameTool.getMonthFromTag("2011 Q3 September");
		Assert.assertEquals(8, month);
		
		exception.expect(NullPointerException.class);
		month = TimeFrameTool.getMonthFromTag("2013 January");
	}
}
