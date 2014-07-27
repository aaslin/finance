package se.aaslin.developer.finance.server.service.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import se.aaslin.developer.finance.server.backend.common.exception.SSUtilDataFormatException;
import se.aaslin.developer.finance.server.backend.common.exception.SSUtilMissingDataException;
import se.aaslin.developer.finance.server.backend.file.importer.FileAccess;
import se.aaslin.developer.finance.shared.dto.file.FileData;
import se.aaslin.developer.finance.shared.dto.file.FileDataset;

@Stateless
public class FileImportServiceImpl implements FileImportServiceLocalBusiness {

	@Inject FileAccountServiceLocalBusiness fileAccounter;
	@Inject FileServiceLocalBusiness fileImportService;
	
	@Override
	public void importFile(String fileName, String tag, InputStream fileStream) {
		FileData fileData = parseFile(fileName, tag, fileStream);
		fileAccounter.account(fileData.getFileDataset());
		fileImportService.saveFile(fileData);
	}
	
	private FileData parseFile(String fileName, String tag, InputStream fileStream) {
		try {
			FileAccess fileAccess = new FileAccess();
			Workbook workbook = WorkbookFactory.create(fileStream);
			Sheet sheet = workbook.getSheetAt(0);
			
			List<FileDataset> datasets = new ArrayList<FileDataset>();
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				FileDataset dataset = new FileDataset();
				dataset.setDate(fileAccess.getDate(row));
				dataset.setTransaction(fileAccess.getTransaction(row));
				dataset.setCost(fileAccess.getCost(row));
				dataset.setFromDB(false);
				datasets.add(dataset);
			}
			
			FileData fileData = new FileData();
			fileData.setFileDataset(datasets);
			fileData.setFileName(fileName);
			fileData.setTag(tag);
			
			return fileData;
			
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SSUtilDataFormatException e) {
			e.printStackTrace();
		} catch (SSUtilMissingDataException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
