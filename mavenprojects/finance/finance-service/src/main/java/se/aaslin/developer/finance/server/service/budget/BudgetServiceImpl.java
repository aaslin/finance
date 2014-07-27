package se.aaslin.developer.finance.server.service.budget;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.Months;

import se.aaslin.developer.finance.db.entity.BudgetRecord;
import se.aaslin.developer.finance.db.entity.TimeFrame;
import se.aaslin.developer.finance.server.dao.BudgetRecordDAO;
import se.aaslin.developer.finance.server.dao.TimeFrameDAO;
import se.aaslin.developer.finance.server.dao.TransactionDAO;
import se.aaslin.developer.finance.server.dao.TransactionDAO.AggregatedCost;
import se.aaslin.developer.finance.server.service.TimeFrameService;
import se.aaslin.developer.finance.shared.dto.BudgetOutcomeDTO;
import se.aaslin.developer.finance.shared.dto.TimeFrameDTO;
import se.aaslin.developer.finance.shared.dto.TimeInterval;
import se.aaslin.developer.finance.shared.dto.category.CategoryType;

@Stateless
public class BudgetServiceImpl implements BudgetServiceLocalBusiness, BudgetServiceWs {

	@Inject TimeFrameService timeFrameService;
	@Inject TimeFrameDAO timeFrameDAO;
	@Inject BudgetRecordDAO budgetRecordDAO;
	@Inject TransactionDAO transactionDAO;
	
	@Override
	public List<BudgetOutcomeDTO> getOutcome(TimeInterval interval) {
		TimeFrame from = getTimeFrame(interval.getStart());
		TimeFrame to = getTimeFrame(interval.getStop());
	
		List<BudgetRecord> budgetRecords = budgetRecordDAO.getBudgetRecords(to.getStop(), CategoryType.EXPENSE);
		List<AggregatedCost> aggregatedCosts = transactionDAO.getAggregatedCosts(from, to);
		int monthsBetween = getMonthsBetween(from, to);
		return createOutcome(budgetRecords, aggregatedCosts, monthsBetween);
	}
	
	@Override
	public List<TimeInterval> getMonthTimeIntervals() {
		List<TimeInterval> result = new ArrayList<TimeInterval>();
		List<TimeFrame> timeFrames = timeFrameDAO.getMonths();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy MMMM");
		for (TimeFrame timeFrame : timeFrames) {
			Date date = timeFrame.getStart();
			result.add(new TimeInterval(dateFormat.format(date), getMonthTimeFrameDTO(date), getMonthTimeFrameDTO(date)));
		}
		
		return result;
	}
	
	@Override
	public List<TimeInterval> getYearTimeIntervals() {
		List<TimeInterval> result = new ArrayList<TimeInterval>();
		List<TimeFrame> timeFrames = timeFrameDAO.getMonths();

		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		SortedMap<String, SortedSet<Date>> yearDates = new TreeMap<String, SortedSet<Date>>(new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return -o1.compareTo(o2);
			}
		});
		for (TimeFrame timeFrame : timeFrames) {
			String year = dateFormat.format(timeFrame.getStart());
			if (!yearDates.containsKey(year)) {
				yearDates.put(year, new TreeSet<Date>());
			}
			yearDates.get(year).add(timeFrame.getStart());
		}
		
		for (String year : yearDates.keySet()) {
			Date start = yearDates.get(year).first();
			Date stop = yearDates.get(year).last();
			result.add(new TimeInterval(year, getMonthTimeFrameDTO(start), getMonthTimeFrameDTO(stop)));
		}
		
		return result;
	}
	
	private List<BudgetOutcomeDTO> createOutcome(List<BudgetRecord> budgetRecords, List<AggregatedCost> aggregatedCosts, int monthsBetween) {
		List<BudgetRecord> budgetTmp = new ArrayList<BudgetRecord>(budgetRecords);
		List<BudgetOutcomeDTO> result = new ArrayList<BudgetOutcomeDTO>();
		for (AggregatedCost cost : aggregatedCosts) {
			BudgetOutcomeDTO dto = new BudgetOutcomeDTO();
			dto.setName(cost.getCategoryName());
			dto.setCost(cost.getAggregatedCost().setScale(2).divide(new BigDecimal(monthsBetween + 1), RoundingMode.HALF_UP));
			BigDecimal budget = getBudget(dto.getName(), budgetTmp);
			dto.setBudget(budget);
			
			result.add(dto);
		}
		
		for (BudgetRecord budgetRecord : budgetTmp) {
			BudgetOutcomeDTO dto = new BudgetOutcomeDTO();
			dto.setName(budgetRecord.getCategory().getName());
			dto.setCost(BigDecimal.ZERO);
			dto.setBudget(budgetRecord.getBudget());
			
			result.add(dto);
		}
		
		return result;
	}

	private BigDecimal getBudget(String categoryName, List<BudgetRecord> budgetRecords) {
		BigDecimal budget = BigDecimal.ZERO;
		Iterator<BudgetRecord> iterator = budgetRecords.iterator();
		while (iterator.hasNext()) {
			BudgetRecord budgetRecord = iterator.next();
			if (categoryName.equals(budgetRecord.getCategory().getName())) {
				budget = budgetRecord.getBudget();
				iterator.remove();
			}
		}
		
		return budget;
	}

	private TimeFrame getTimeFrame(TimeFrameDTO dto) {
		TimeFrame timeFrame = new TimeFrame();
		timeFrame.setId(dto.getId());
		timeFrame.setStart(dto.getStart());
		timeFrame.setStop(dto.getStop());
		timeFrame.setTag(dto.getTag());
		
		return timeFrame;
	}
	
	private TimeFrameDTO getMonthTimeFrameDTO(Date date) {
		TimeFrame timeFrame = timeFrameService.getOrCreateMonthTimeFrame(date);
		TimeFrameDTO dto = new TimeFrameDTO();
		dto.setId(timeFrame.getId());
		dto.setStart(timeFrame.getStart());
		dto.setStop(timeFrame.getStop());
		dto.setTag(timeFrame.getTag());
		
		return dto;
	}

	private int getMonthsBetween(TimeFrame from, TimeFrame to) {
		return Months.monthsBetween(new DateTime(from.getStart()), new DateTime(to.getStart())).getMonths();
	}
}
