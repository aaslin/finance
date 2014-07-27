package se.aaslin.developer.finance.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BudgetOutcomeDTO implements Serializable {
	
	private static final long serialVersionUID = 5609727761993178228L;
	
	private String name;
	private BigDecimal cost;
	private BigDecimal budget;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getBudget() {
		return budget;
	}

	public void setBudget(BigDecimal budget) {
		this.budget = budget;
	}
}
