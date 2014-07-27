package se.aaslin.developer.finance.server.dao;

import java.util.Date;
import java.util.List;

import se.aaslin.developer.finance.db.entity.File;
import se.aaslin.developer.finance.shared.dto.file.FileDTO;

public interface FileDAO extends GenericDAO<Integer, File> {

	List<FileDTO> listAllFiles();

	File findByName(String fileName);
	
	File findFile(Date date);
}
