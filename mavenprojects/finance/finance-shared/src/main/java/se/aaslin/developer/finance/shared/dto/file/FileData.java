package se.aaslin.developer.finance.shared.dto.file;

import java.io.Serializable;
import java.util.List;

public class FileData implements Serializable {
	
	private static final long serialVersionUID = -3960535148452025070L;
	
	private String fileName; 
	private String tag;
	private List<FileDataset> fileDataset;
	
	public FileData() {
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<FileDataset> getFileDataset() {
		return fileDataset;
	}

	public void setFileDataset(List<FileDataset> fileDataset) {
		this.fileDataset = fileDataset;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileData other = (FileData) obj;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		return true;
	}
}
