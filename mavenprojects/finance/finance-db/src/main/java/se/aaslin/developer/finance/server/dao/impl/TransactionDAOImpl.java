package se.aaslin.developer.finance.server.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import se.aaslin.developer.finance.db.entity.Category;
import se.aaslin.developer.finance.db.entity.Category_;
import se.aaslin.developer.finance.db.entity.File;
import se.aaslin.developer.finance.db.entity.File_;
import se.aaslin.developer.finance.db.entity.TimeFrame;
import se.aaslin.developer.finance.db.entity.TimeFrame_;
import se.aaslin.developer.finance.db.entity.Transaction;
import se.aaslin.developer.finance.db.entity.Transaction_;
import se.aaslin.developer.finance.server.dao.TransactionDAO;
import se.aaslin.developer.finance.shared.dto.category.CategoryType;
import se.aaslin.developer.finance.shared.dto.file.FileDataset;

@Stateless
public class TransactionDAOImpl extends GenericDAOImpl<Integer, Transaction> implements TransactionDAO {

	@PersistenceContext EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	public List<FileDataset> getFileData(String fileName) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<FileDataset> query = cb.createQuery(FileDataset.class);
		
		Root<Transaction> transaction = query.from(Transaction.class);
		Join<Transaction, File> file = transaction.join(Transaction_.file);
		Join<Transaction, Category> category = transaction.join(Transaction_.category, JoinType.LEFT);
		
		query.multiselect(transaction.get(Transaction_.id), 
				transaction.get(Transaction_.date), transaction.get(Transaction_.comment),
				category.get(Category_.name), transaction.get(Transaction_.cost));
		query.where(cb.equal(file.get(File_.name), fileName));
		query.orderBy(cb.desc(transaction.get(Transaction_.date)));
		
		return em.createQuery(query).getResultList();
	}

	@Override
	public void deleteFileData(File file) {
		Query query = em.createQuery("DELETE FROM Transaction t WHERE t.file = :file");
		query.setParameter("file", file);
		query.executeUpdate();
	}

	@Override
	public List<AggregatedCost> getAggregatedCosts(TimeFrame fromMonth, TimeFrame toMonth) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<AggregatedCost> query = cb.createQuery(AggregatedCost.class);
		
		Root<Transaction> transaction = query.from(Transaction.class);
		Join<Transaction, Category> category = transaction.join(Transaction_.category);
		Join<Transaction, File> file = transaction.join(Transaction_.file);
		Join<File, TimeFrame> timeFrame = file.join(File_.timeFrame);
		
		Path<String> categoryNamePath = category.get(Category_.name);
		Expression<BigDecimal> aggregatedCost = cb.sum(transaction.get(Transaction_.cost));
		
		Predicate p1 = cb.greaterThanOrEqualTo(timeFrame.get(TimeFrame_.start), fromMonth.getStart());
		Predicate p2 = cb.lessThanOrEqualTo(timeFrame.get(TimeFrame_.stop), toMonth.getStop());
		Predicate p3 = cb.equal(category.get(Category_.type), CategoryType.EXPENSE);
		
		query.multiselect(categoryNamePath, aggregatedCost);
		query.where(p1, p2, p3);
		query.groupBy(categoryNamePath);
		
		return em.createQuery(query).getResultList();
	}

	@Override
	public List<Transaction> getTransactions(Date from) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Transaction> query = cb.createQuery(Transaction.class);
		
		Root<Transaction> transaction = query.from(Transaction.class);
		
		Path<Date> datePath = transaction.get(Transaction_.date);
		Predicate p1 = cb.greaterThanOrEqualTo(datePath, from);
		
		query.select(transaction);
		query.where(p1);
		
		return em.createQuery(query).getResultList();
	}
}
