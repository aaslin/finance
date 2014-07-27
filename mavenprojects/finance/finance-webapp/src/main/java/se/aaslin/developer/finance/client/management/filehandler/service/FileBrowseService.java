package se.aaslin.developer.finance.client.management.filehandler.service;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import se.aaslin.developer.finance.shared.dto.file.FileDTO;

@Path("ws")
public interface FileBrowseService extends RestService {

	@POST
	@Path("fileImport/updateFile")
	void update(FileDTO dto, MethodCallback<Void> callback);

	@POST
	@Path("fileImport/removeFile")
	void remove(FileDTO dto, MethodCallback<List<FileDTO>> callback);
	
	@POST
	@Path("fileImport/getFiles")
	void getFiles(MethodCallback<List<FileDTO>> callback);
}
