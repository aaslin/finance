package se.aaslin.developer.finance.client.finance.budget.service;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import se.aaslin.developer.finance.shared.dto.BudgetOutcomeDTO;
import se.aaslin.developer.finance.shared.dto.TimeInterval;

@Path("ws")
public interface AbstractBudgetOutcomeService extends RestService {
	
	@POST
	@Path("budget/getOutcome")
	void getOutome(TimeInterval interval, MethodCallback<List<BudgetOutcomeDTO>> callback);
	
	void getTimeIntervals(MethodCallback<List<TimeInterval>> callback);
}
