package se.aaslin.developer.finance.server.service.budget;

import java.util.List;

import se.aaslin.developer.finance.shared.dto.BudgetOutcomeDTO;
import se.aaslin.developer.finance.shared.dto.TimeInterval;

public interface BudgetService {

	List<BudgetOutcomeDTO> getOutcome(TimeInterval interval);
	
	List<TimeInterval> getMonthTimeIntervals();
	
	List<TimeInterval> getYearTimeIntervals();
}
