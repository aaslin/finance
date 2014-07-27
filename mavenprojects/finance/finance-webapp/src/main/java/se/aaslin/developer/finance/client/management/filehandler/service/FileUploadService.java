package se.aaslin.developer.finance.client.management.filehandler.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

@Path("ws")
public interface FileUploadService extends RestService {
	
	@POST
	@Path("fileImport/doFileExist")
	void doFileExist(@QueryParam("fileName") String fileName, MethodCallback<Boolean> callback);
}
