package se.aaslin.developer.finance.server.service.category;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import se.aaslin.developer.finance.shared.dto.category.CategoryDTO;
import se.aaslin.developer.finance.shared.dto.category.CategoryRuleDTO;

@Local
@Path("category")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface CategoryServiceWs extends CategoryService {

	@Override
	@GET
	@Path("getCategories")
	List<CategoryDTO> getCategories();
	
	@Override
	@POST
	@Path("removeCategory")
	void removeCategory(CategoryDTO dto);
	
	@Override
	@POST
	@Path("saveCategories")
	List<CategoryDTO> saveCategories(List<CategoryDTO> dtos);

	@GET
	@Path("getCategoryRules")
	List<CategoryRuleDTO> getCategoryRules();

	@POST
	@Path("removeCategoryRule")
	void removeCategoryRule(CategoryRuleDTO rule);
	
	@POST
	@Path("saveCategoryRule")
	List<CategoryRuleDTO> saveCategoryRule(List<CategoryRuleDTO> dtos);
}
