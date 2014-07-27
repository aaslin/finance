package se.aaslin.developer.finance.server.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import se.aaslin.developer.finance.db.entity.BudgetRecord;
import se.aaslin.developer.finance.db.entity.BudgetRecord_;
import se.aaslin.developer.finance.db.entity.Category;
import se.aaslin.developer.finance.db.entity.Category_;
import se.aaslin.developer.finance.server.dao.BudgetRecordDAO;
import se.aaslin.developer.finance.shared.dto.category.CategoryType;

@Stateless
public class BudgetRecordDAOImpl extends GenericDAOImpl<Integer, BudgetRecord> implements BudgetRecordDAO {

	@PersistenceContext EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	public List<BudgetRecord> getBudgetRecords(Date date, CategoryType costType) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BudgetRecord> query = cb.createQuery(BudgetRecord.class);
		
		Root<BudgetRecord> budgetRecord = query.from(BudgetRecord.class);
		Join<BudgetRecord, Category> category = budgetRecord.join(BudgetRecord_.category);
		budgetRecord.fetch(BudgetRecord_.category);
		
		Subquery<Date> subQuery = query.subquery(Date.class);
		Root<BudgetRecord> budgetRecord2 = subQuery.from(BudgetRecord.class);
		
		Predicate p1 = cb.lessThanOrEqualTo(budgetRecord.get(BudgetRecord_.date), date);
		Predicate p2 = cb.equal(budgetRecord2.get(BudgetRecord_.category), budgetRecord.get(BudgetRecord_.category));
		Predicate p3 = cb.equal(category.get(Category_.type), costType);
		
		subQuery.select(cb.greatest(budgetRecord2.get(BudgetRecord_.date)));
		subQuery.where(p1, p2, p3);
		
		Predicate p4 = cb.equal(budgetRecord.get(BudgetRecord_.date), subQuery);
		
		query.select(budgetRecord);
		query.where(p3, p4);
		
		return em.createQuery(query).getResultList();
	}

	@Override
	public SortedMap<String, List<BudgetRecord>> getAllBudgetRecords(CategoryType costType) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BudgetRecord> query = cb.createQuery(BudgetRecord.class);
		
		Root<BudgetRecord> budgetRecord = query.from(BudgetRecord.class);
		Join<BudgetRecord, Category> category = budgetRecord.join(BudgetRecord_.category);
		budgetRecord.fetch(BudgetRecord_.category);
		
		Predicate p1 = cb.equal(category.get(Category_.type), costType);
		
		query.select(budgetRecord);
		query.where(p1);
		
		List<BudgetRecord> records = em.createQuery(query).getResultList();
		
		SortedMap<String, List<BudgetRecord>> result = new TreeMap<String, List<BudgetRecord>>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy MMMM");
		
		for (BudgetRecord record : records) {
			String tag = dateFormat.format(record.getDate());
			if (!result.containsKey(tag)) {
				result.put(tag, new ArrayList<BudgetRecord>());
			}
			result.get(tag).add(record);
		}
		
		return result;
	}
}
