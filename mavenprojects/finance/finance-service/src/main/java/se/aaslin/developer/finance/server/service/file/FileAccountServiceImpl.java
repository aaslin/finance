package se.aaslin.developer.finance.server.service.file;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.inject.Inject;

import se.aaslin.developer.finance.db.entity.Category;
import se.aaslin.developer.finance.db.entity.CategoryRule;
import se.aaslin.developer.finance.db.entity.Transaction;
import se.aaslin.developer.finance.server.dao.CategoryRuleDAO;
import se.aaslin.developer.finance.shared.dto.file.FileDataset;

@Stateless
public class FileAccountServiceImpl implements FileAccountServiceLocalBusiness {

	@Inject CategoryRuleDAO categoryRuleDAO;

	@Override
	public void account(List<FileDataset> datasets) {
		List<FileDataset> temp = new ArrayList<FileDataset>(datasets);
		for (CategoryRule rule : categoryRuleDAO.list()) {
			Iterator<FileDataset> it = temp.iterator();
			while (it.hasNext()) {
				FileDataset dataset = it.next();
				if (isMatch(rule, dataset.getTransaction())) {
					dataset.setCategory(rule.getCategory().getName());
					it.remove();
				}
			}
		}

		// return not categorized if necessary.
	}
	

	@Override
	public Category account(Transaction transaction) {
		for (CategoryRule rule : categoryRuleDAO.list()) {
			if (isMatch(rule, transaction)) {
				return rule.getCategory();
			}
		}
		
		return null;
	}

	private boolean isMatch(CategoryRule rule, Transaction transaction) {
		return isMatch(rule, transaction.getComment());
	}

	private boolean isMatch(CategoryRule rule, String comment) {
		switch (rule.getOperator()) {
		case CONTAINS:
			return comment.contains(rule.getPattern());
		case EQUALS:
			return comment.equals(rule.getPattern());
		case MATCHES:
			Pattern pattern = Pattern.compile(rule.getPattern());
			Matcher matcher = pattern.matcher(comment);
			return matcher.find();
		}
		
		return false;
	}
}
