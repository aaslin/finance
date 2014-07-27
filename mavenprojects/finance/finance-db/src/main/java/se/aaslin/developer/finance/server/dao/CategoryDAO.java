package se.aaslin.developer.finance.server.dao;

import se.aaslin.developer.finance.db.entity.Category;

public interface CategoryDAO extends GenericDAO<Integer, Category> {

	Category getOrCreate(String category);
}
