package se.aaslin.developer.finance.server.service.category;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import se.aaslin.developer.finance.db.entity.Category;
import se.aaslin.developer.finance.db.entity.CategoryRule;
import se.aaslin.developer.finance.server.dao.CategoryDAO;
import se.aaslin.developer.finance.server.dao.CategoryRuleDAO;
import se.aaslin.developer.finance.shared.dto.category.CategoryDTO;
import se.aaslin.developer.finance.shared.dto.category.CategoryRuleDTO;

@Stateless
public class CategoryServiceImpl implements CategoryServiceLocalBusiness, CategoryServiceWs {

	@Inject CategoryDAO categoryDAO;
	@Inject CategoryRuleDAO categoryRuleDAO;
	
	@Override
	public List<CategoryDTO> getCategories() {
		return createCategoryDTOs(categoryDAO.list());
	}

	private List<CategoryDTO> createCategoryDTOs(List<Category> list) {
		List<CategoryDTO> result = new ArrayList<CategoryDTO>();
		for (Category category : list) {
			CategoryDTO dto = createCategoryDTO(category);
			result.add(dto);
		}
		
		return result;
	}

	private CategoryDTO createCategoryDTO(Category category) {
		CategoryDTO dto = new CategoryDTO();
		dto.setChanged(false);
		dto.setFromDB(true);
		dto.setId(category.getId());
		dto.setName(category.getName());
		dto.setType(category.getType());
		return dto;
	}

	@Override
	public void removeCategory(CategoryDTO dto) {
		categoryDAO.delete(categoryDAO.merge(createCategoryEntity(dto)));
	}

	private Category createCategoryEntity(CategoryDTO dto) {
		Category category = new Category();
		category.setId(dto.getId());
		category.setName(dto.getName());
		category.setType(dto.getType());
		
		return category;
	}

	@Override
	public List<CategoryDTO> saveCategories(List<CategoryDTO> dtos) {
		for (CategoryDTO categoryDTO : dtos) {
			if (categoryDTO.isFromDB() && categoryDTO.getId() != 0) {
				categoryDAO.merge(createCategoryEntity(categoryDTO));
			} else {
				categoryDAO.create(createCategoryEntity(categoryDTO));
			}
		}
		
		return getCategories();
	}

	@Override
	public List<CategoryRuleDTO> getCategoryRules() {
		return createRuleDTOs(categoryRuleDAO.list());
	}

	private List<CategoryRuleDTO> createRuleDTOs(List<CategoryRule> rules) {
		List<CategoryRuleDTO> result = new ArrayList<CategoryRuleDTO>();
		for (CategoryRule rule : rules) {
			CategoryRuleDTO ruleDTO = new CategoryRuleDTO();
			CategoryDTO categoryDTO = createCategoryDTO(rule.getCategory());
			ruleDTO.setCategory(categoryDTO);
			ruleDTO.setChanged(false);
			ruleDTO.setEnabled(rule.isEnabled());
			ruleDTO.setFromDB(true);
			ruleDTO.setId(rule.getId());
			ruleDTO.setName(rule.getName());
			ruleDTO.setOperator(rule.getOperator());
			ruleDTO.setPattern(rule.getPattern());
			result.add(ruleDTO);
		}
		
		return result;
	}

	@Override
	public void removeCategoryRule(CategoryRuleDTO rule) {
		categoryRuleDAO.delete(categoryRuleDAO.merge(createRuleEntity(rule)));
	}

	private CategoryRule createRuleEntity(CategoryRuleDTO dto) {
		CategoryRule rule = new CategoryRule();
		Category category = createCategoryEntity(dto.getCategory());
		rule.setCategory(category);
		rule.setEnabled(dto.isEnabled());
		rule.setId(dto.getId());
		rule.setName(dto.getName());
		rule.setOperator(dto.getOperator());
		rule.setPattern(dto.getPattern());
		
		return rule;
	}

	@Override
	public List<CategoryRuleDTO> saveCategoryRule(List<CategoryRuleDTO> dtos) {
		for (CategoryRuleDTO categoryRuleDTO : dtos) {
			if (categoryRuleDTO.isFromDB() && categoryRuleDTO.getId() != 0) {
				categoryRuleDAO.merge(createRuleEntity(categoryRuleDTO));
			} else {
				categoryRuleDAO.create(createRuleEntity(categoryRuleDTO));
			}
		}
		
		return getCategoryRules();
	}
}
