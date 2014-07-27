package se.aaslin.developer.finance.server.service.impl;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import se.aaslin.developer.finance.db.entity.TimeFrame;
import se.aaslin.developer.finance.server.backend.util.timeframe.TimeFrameGenerator;
import se.aaslin.developer.finance.server.backend.util.timeframe.TimeFrameTool;
import se.aaslin.developer.finance.server.dao.TimeFrameDAO;
import se.aaslin.developer.finance.server.service.TimeFrameService;

@Stateless
public class TimeFrameServiceImpl implements TimeFrameService {
	
	@Inject TimeFrameDAO timeFrameDAO;
	
	@Override
	public TimeFrame getOrCreateMonthTimeFrame(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, cal.getActualMinimum(Calendar.DATE));
		
		return getOrCreateMonthTimeFrame(cal.getTime());
	}

	@Override
	public TimeFrame getOrCreateMonthTimeFrame(String tag) {
		int year = TimeFrameTool.getYearFromTag(tag);
		int month = TimeFrameTool.getMonthFromTag(tag);
		
		return getOrCreateMonthTimeFrame(year, month);
	}

	@Override
	public TimeFrame getOrCreateMonthTimeFrame(Date date) {
		TimeFrame timeFrame = timeFrameDAO.getMonthTimeFrame(date);
		if (timeFrame == null) {
			timeFrame = TimeFrameGenerator.createMonthTimeFrame(date);
			timeFrameDAO.create(timeFrame);
		}
		
		return timeFrame;
	}
}
