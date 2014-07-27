package se.aaslin.developer.finance.server.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import se.aaslin.developer.finance.db.entity.CategoryRule;
import se.aaslin.developer.finance.server.dao.CategoryRuleDAO;

public class CategoryRuleDAOImpl extends GenericDAOImpl<Integer, CategoryRule> implements CategoryRuleDAO {

	@PersistenceContext EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
}
