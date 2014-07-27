package se.aaslin.developer.finance.server.service.file;

import java.util.List;

import se.aaslin.developer.finance.shared.dto.file.FileDTO;
import se.aaslin.developer.finance.shared.dto.file.FileData;

public interface FileService {
	
	void saveFile(FileData fileData);
	
	FileData getFile(String fileName);
	
	List<FileDTO> getFiles();

	boolean doFileExist(String fileName);

	void updateFile(FileDTO dto);

	List<FileDTO> removeFile(FileDTO dto);
	
	void removeTransaction(Integer id);
}
