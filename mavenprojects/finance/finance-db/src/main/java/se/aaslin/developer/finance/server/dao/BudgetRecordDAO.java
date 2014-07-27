package se.aaslin.developer.finance.server.dao;

import java.util.Date;
import java.util.List;
import java.util.SortedMap;

import se.aaslin.developer.finance.db.entity.BudgetRecord;
import se.aaslin.developer.finance.shared.dto.category.CategoryType;

public interface BudgetRecordDAO extends GenericDAO<Integer, BudgetRecord> {
	
	List<BudgetRecord> getBudgetRecords(Date date, CategoryType costType);
	
	SortedMap<String, List<BudgetRecord>> getAllBudgetRecords(CategoryType costType);
}
