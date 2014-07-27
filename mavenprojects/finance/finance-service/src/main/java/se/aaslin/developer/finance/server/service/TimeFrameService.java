package se.aaslin.developer.finance.server.service;

import java.util.Date;

import se.aaslin.developer.finance.db.entity.TimeFrame;

public interface TimeFrameService {
	
	TimeFrame getOrCreateMonthTimeFrame(int year, int month);

	TimeFrame getOrCreateMonthTimeFrame(String tag);

	TimeFrame getOrCreateMonthTimeFrame(Date tag);
}
