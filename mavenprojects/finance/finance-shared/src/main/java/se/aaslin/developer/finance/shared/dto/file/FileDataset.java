package se.aaslin.developer.finance.shared.dto.file;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FileDataset implements Serializable {
	
	private static final long serialVersionUID = 6124564846007389307L;
	
	private int id;
	private Date date;
	private String transaction;
	private String category;
	private BigDecimal cost;
	private boolean fromDB;
	private boolean changed;
	
	public FileDataset() {
	}

	public FileDataset(int id, Date date, String transaction, String category, BigDecimal cost) {
		this.id = id;
		this.date = date;
		this.transaction = transaction;
		this.category = category;
		this.cost = cost;
		this.fromDB = true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}


	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isFromDB() {
		return fromDB;
	}

	public void setFromDB(boolean fromDB) {
		this.fromDB = fromDB;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		FileDataset other = (FileDataset) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FileDataset [transaction=" + transaction + ", category=" + category + "]";
	}
}
