package se.aaslin.developer.finance.server.service.budget;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import se.aaslin.developer.finance.shared.dto.BudgetOutcomeDTO;
import se.aaslin.developer.finance.shared.dto.TimeInterval;

@Local
@Path("budget")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface BudgetServiceWs extends BudgetService {

	@POST
	@Path("getOutcome")
	List<BudgetOutcomeDTO> getOutcome(TimeInterval interval);
	
	@GET
	@Path("getMonthTimeIntervals")
	List<TimeInterval> getMonthTimeIntervals();
	
	@GET
	@Path("getYearTimeIntervals")
	List<TimeInterval> getYearTimeIntervals();
}
