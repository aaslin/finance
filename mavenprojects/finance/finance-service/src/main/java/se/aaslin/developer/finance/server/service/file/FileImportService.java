package se.aaslin.developer.finance.server.service.file;

import java.io.InputStream;

public interface FileImportService {

	void importFile(String fileName, String tag, InputStream fileStream);
}
