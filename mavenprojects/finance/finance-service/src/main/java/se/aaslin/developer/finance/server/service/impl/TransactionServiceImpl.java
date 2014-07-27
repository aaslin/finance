package se.aaslin.developer.finance.server.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import se.aaslin.developer.finance.db.entity.Category;
import se.aaslin.developer.finance.db.entity.File;
import se.aaslin.developer.finance.db.entity.Transaction;
import se.aaslin.developer.finance.server.dao.CategoryDAO;
import se.aaslin.developer.finance.server.dao.FileDAO;
import se.aaslin.developer.finance.server.dao.TransactionDAO;
import se.aaslin.developer.finance.server.service.TransactionService;
import se.aaslin.developer.finance.shared.dto.file.FileData;
import se.aaslin.developer.finance.shared.dto.file.FileDataset;

@Stateless
public class TransactionServiceImpl implements TransactionService {

	@Inject TransactionDAO transactionDAO;
	@Inject CategoryDAO categoryDAO;
	@Inject FileDAO fileDAO;
	
	@Override
	public void saveTransactions(FileData fileData) {
		File file = fileDAO.findByName(fileData.getFileName());
		List<Category> categories = categoryDAO.list();

		for (FileDataset dataset : fileData.getFileDataset()) {
			if (dataset.isFromDB() && !dataset.isChanged()) {
				continue;
			}
			Transaction transaction = new Transaction();
			transaction.setId(dataset.getId());
			transaction.setComment(dataset.getTransaction());
			transaction.setCost(dataset.getCost());
			transaction.setDate(dataset.getDate());
			transaction.setFile(file);

			Category category = getCategory(dataset.getCategory(), categories);
			transaction.setCategory(category);

			if (dataset.isFromDB()) {
				transactionDAO.merge(transaction);
			} else {
				transactionDAO.create(transaction);
			}
		}
	}
	
	private Category getCategory(String category, List<Category> categories) {
		for (Category c : categories) {
			if (c.getName().equals(category)) {
				return c;
			}
		}
		
		return null;
	}
}
