package se.aaslin.developer.finance.client.management.category.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import se.aaslin.developer.finance.shared.dto.category.CategoryDTO;

@Path("ws")
public interface CategoryBrowseService extends RestService {

	@GET
	@Path("category/getCategories")
	void getCategories(MethodCallback<List<CategoryDTO>> callback);

	@POST
	@Path("category/removeCategory")
	void removeCategory(CategoryDTO dto, MethodCallback<Void> callback);
	
	@POST
	@Path("category/saveCategories")
	void saveCategories(List<CategoryDTO> dtos, MethodCallback<List<CategoryDTO>> callback);
}
