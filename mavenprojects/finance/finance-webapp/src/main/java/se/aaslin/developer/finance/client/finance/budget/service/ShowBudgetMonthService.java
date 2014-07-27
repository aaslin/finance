package se.aaslin.developer.finance.client.finance.budget.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.fusesource.restygwt.client.MethodCallback;

import se.aaslin.developer.finance.shared.dto.TimeInterval;

@Path("ws")
public interface ShowBudgetMonthService extends AbstractBudgetOutcomeService {
	
	@GET
	@Path("budget/getMonthTimeIntervals")
	void getTimeIntervals(MethodCallback<List<TimeInterval>> callback);
}
