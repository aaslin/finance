package se.aaslin.developer.finance.server.service.file;

import java.util.List;

import se.aaslin.developer.finance.db.entity.Category;
import se.aaslin.developer.finance.db.entity.Transaction;
import se.aaslin.developer.finance.shared.dto.file.FileDataset;

public interface FileAccountService {

	void account(List<FileDataset> datasets);
	
	Category account(Transaction transaction);
}
