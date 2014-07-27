package se.aaslin.developer.finance.server.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import se.aaslin.developer.finance.db.entity.File;
import se.aaslin.developer.finance.db.entity.TimeFrame;
import se.aaslin.developer.finance.db.entity.Transaction;
import se.aaslin.developer.finance.shared.dto.file.FileDataset;

public interface TransactionDAO extends GenericDAO<Integer, Transaction> {
	public static class AggregatedCost {

		private final String categoryName;
		private final BigDecimal aggregatedCost;

		public AggregatedCost(String categoryName, BigDecimal aggregatedCost) {
			this.categoryName = categoryName;
			this.aggregatedCost = aggregatedCost;
		}

		public String getCategoryName() {
			return categoryName;
		}

		public BigDecimal getAggregatedCost() {
			return aggregatedCost;
		}
	}

	List<FileDataset> getFileData(String fileName);

	void deleteFileData(File file);

	List<AggregatedCost> getAggregatedCosts(TimeFrame fromMonth, TimeFrame toMonth);
	
	List<Transaction> getTransactions(Date from);
}
