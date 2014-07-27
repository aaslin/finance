package se.aaslin.developer.finance.shared.dto;

import java.io.Serializable;
import java.util.List;

public class BudgetOutcomesDTO implements Serializable {

	private static final long serialVersionUID = -1391106106437661774L;

	private String yearMonthTag;
	private List<BudgetOutcomeDTO> budgetOutcomeDTOs;
	
	public String getYearMonthTag() {
		return yearMonthTag;
	}
	
	public void setYearMonthTag(String yearMonthTag) {
		this.yearMonthTag = yearMonthTag;
	}
	
	public List<BudgetOutcomeDTO> getBudgetOutcomeDTOs() {
		return budgetOutcomeDTOs;
	}
	
	public void setBudgetOutcomeDTOs(List<BudgetOutcomeDTO> budgetOutcomeDTOs) {
		this.budgetOutcomeDTOs = budgetOutcomeDTOs;
	}
}
