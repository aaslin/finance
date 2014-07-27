package se.aaslin.developer.finance.server.service.file;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import se.aaslin.developer.finance.shared.dto.file.FileDTO;
import se.aaslin.developer.finance.shared.dto.file.FileData;

@Local
@Path("fileImport")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface FileServiceWs extends FileService {
	
	@Override
	@POST
	@Path("saveFile")
	void saveFile(FileData fileData);
	
	@Override
	@POST
	@Path("getFile")
	FileData getFile(@QueryParam("fileName") String fileName);
	
	@Override
	@POST
	@Path("getFiles")
	List<FileDTO> getFiles();

	@Override
	@POST
	@Path("doFileExist")
	boolean doFileExist(String fileName);

	@Override
	@POST
	@Path("updateFile")
	void updateFile(FileDTO dto);

	@Override
	@POST
	@Path("removeFile")
	List<FileDTO> removeFile(FileDTO dto);
	
	@Override
	@DELETE
	@Path("removeTransaction")
	void removeTransaction(@QueryParam("id") Integer id);
}
