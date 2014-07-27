package se.aaslin.developer.finance.server.service.file;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.QueryParam;

import se.aaslin.developer.finance.db.entity.Category;
import se.aaslin.developer.finance.db.entity.File;
import se.aaslin.developer.finance.db.entity.TimeFrame;
import se.aaslin.developer.finance.db.entity.Transaction;
import se.aaslin.developer.finance.server.dao.CategoryDAO;
import se.aaslin.developer.finance.server.dao.FileDAO;
import se.aaslin.developer.finance.server.dao.TransactionDAO;
import se.aaslin.developer.finance.server.service.TimeFrameService;
import se.aaslin.developer.finance.shared.dto.file.FileDTO;
import se.aaslin.developer.finance.shared.dto.file.FileData;
import se.aaslin.developer.finance.shared.dto.file.FileDataset;

@Stateless
public class FileServiceImpl implements FileServiceLocalBusiness, FileServiceWs {

	@Inject FileDAO fileDAO;
	@Inject TransactionDAO transactionDAO;
	@Inject CategoryDAO categoryDAO;
	@Inject TimeFrameService timeFrameService;
	
	@Override
	public void saveFile(FileData fileData) {
		File file = fileDAO.findByName(fileData.getFileName());
		TimeFrame timeFrame = timeFrameService.getOrCreateMonthTimeFrame(fileData.getTag());
		
		if (file != null) {
			file.setTimeFrame(timeFrame);
			fileDAO.merge(file);
		} else {
			file = new File();
			file.setName(fileData.getFileName());
			file.setTimeFrame(timeFrame);
			fileDAO.create(file);
		}
		
		List<Category> categories = categoryDAO.list();

		for (FileDataset dataset : fileData.getFileDataset()) {
			if (dataset.isFromDB() && !dataset.isChanged()) {
				continue;
			}
			Transaction transaction = new Transaction();
			transaction.setId(dataset.getId());
			transaction.setComment(dataset.getTransaction());
			transaction.setCost(dataset.getCost());
			transaction.setDate(dataset.getDate());
			transaction.setFile(file);

			Category category = getCategory(dataset.getCategory(), categories);
			transaction.setCategory(category);

			if (dataset.isFromDB()) {
				transactionDAO.merge(transaction);
			} else {
				transactionDAO.create(transaction);
			}
		}
	}

	@Override
	public FileData getFile(String fileName) {
		File file = fileDAO.findByName(fileName);
		List<FileDataset> datasets = transactionDAO.getFileData(fileName);
		FileData fileData = new FileData();
		fileData.setFileDataset(datasets);
		fileData.setFileName(file.getName());
		fileData.setTag(file.getTimeFrame().getTag());
		
		return fileData;
	}

	@Override
	public List<FileDTO> getFiles() {
		return fileDAO.listAllFiles();
	}
	
	private Category getCategory(String category, List<Category> categories) {
		for (Category c : categories) {
			if (c.getName().equals(category)) {
				return c;
			}
		}
		
		return null;
	}
	
	@Override
	public boolean doFileExist(@QueryParam("fileName") String fileName) {
		return fileDAO.findByName(fileName) != null;
	}
	
	@Override
	public void updateFile(FileDTO dto) {
		File managedFile = fileDAO.findByName(dto.getName());
		TimeFrame timeFrame = timeFrameService.getOrCreateMonthTimeFrame(dto.getTag());
		managedFile.setName(dto.getName());
		managedFile.setTimeFrame(timeFrame);
	}
	
	@Override
	public List<FileDTO> removeFile(FileDTO dto) {
		File file = fileDAO.findByName(dto.getName());
		transactionDAO.deleteFileData(file);
		fileDAO.delete(file);
		
		return getFiles();
	}

	@Override
	public void removeTransaction(Integer id) {
		Transaction transaction = transactionDAO.findById(id);
		transactionDAO.delete(transaction);
	}
}
