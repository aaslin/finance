package se.aaslin.developer.finance.server.service;

import se.aaslin.developer.finance.shared.dto.file.FileData;

public interface TransactionService {
	
	void saveTransactions(FileData fileData);
}
