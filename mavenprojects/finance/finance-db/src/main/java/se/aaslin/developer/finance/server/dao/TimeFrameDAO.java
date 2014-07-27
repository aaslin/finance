package se.aaslin.developer.finance.server.dao;

import java.util.Date;
import java.util.List;

import se.aaslin.developer.finance.db.entity.TimeFrame;

public interface TimeFrameDAO extends GenericDAO<Integer, TimeFrame> {

	TimeFrame getMonthTimeFrame(Date time);
	
	List<TimeFrame> getMonths();
}
