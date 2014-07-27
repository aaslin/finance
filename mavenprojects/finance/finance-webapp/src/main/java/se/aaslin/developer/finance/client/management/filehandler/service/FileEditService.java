package se.aaslin.developer.finance.client.management.filehandler.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import se.aaslin.developer.finance.shared.dto.category.CategoryDTO;
import se.aaslin.developer.finance.shared.dto.file.FileData;

@Path("ws")
public interface FileEditService extends RestService {
	
	@POST
	@Path("fileImport/getFile")
	public void getFile(@QueryParam("fileName") String fileName, MethodCallback<FileData> callback);
	
	@POST
	@Path("fileImport/saveFile")
	public void persistFile(FileData fileData, MethodCallback<Void> callback);

	@GET
	@Path("category/getCategories")
	public void getCategories(MethodCallback<List<CategoryDTO>> callback);
	
	@DELETE
	@Path("fileImport/removeTransaction")
	void removeTransaction(@QueryParam("id") Integer id, MethodCallback<Void> callback);
}
