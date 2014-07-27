package se.aaslin.developer.finance.server.service.category;

import java.util.List;

import se.aaslin.developer.finance.shared.dto.category.CategoryDTO;
import se.aaslin.developer.finance.shared.dto.category.CategoryRuleDTO;

public interface CategoryService {

	List<CategoryDTO> getCategories();
	
	void removeCategory(CategoryDTO dto);
	
	List<CategoryDTO> saveCategories(List<CategoryDTO> dtos);
	
	List<CategoryRuleDTO> getCategoryRules();

	void removeCategoryRule(CategoryRuleDTO rule);
	
	List<CategoryRuleDTO> saveCategoryRule(List<CategoryRuleDTO> dtos);
}
