package se.aaslin.developer.finance.client.management.category.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import se.aaslin.developer.finance.shared.dto.category.CategoryDTO;
import se.aaslin.developer.finance.shared.dto.category.CategoryRuleDTO;

@Path("ws")
public interface CategoryRuleService extends RestService {

	@GET
	@Path("category/getCategories")
	void getCategories(MethodCallback<List<CategoryDTO>> callback);
	
	@GET
	@Path("category/getCategoryRules")
	void getCategoryRules(MethodCallback<List<CategoryRuleDTO>> callback);

	@POST
	@Path("category/removeCategoryRule")
	void removeCategoryRule(CategoryRuleDTO rule, MethodCallback<Void> callback);
	
	@POST
	@Path("category/saveCategoryRule")
	void saveCategoryRule(List<CategoryRuleDTO> dtos, MethodCallback<List<CategoryRuleDTO>> callback);
}
