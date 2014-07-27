package se.aaslin.developer.finance.server.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import se.aaslin.developer.finance.db.entity.Category;
import se.aaslin.developer.finance.server.dao.CategoryDAO;

@Stateless
public class CategoryDAOImpl extends GenericDAOImpl<Integer, Category> implements CategoryDAO {

	@PersistenceContext EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	public Category getOrCreate(String category) {
		return null;
	}
}
