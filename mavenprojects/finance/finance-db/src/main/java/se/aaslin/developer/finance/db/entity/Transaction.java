package se.aaslin.developer.finance.db.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "`transaction`")
public class Transaction {
	
	@Id
	@Column(name = "`id`")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "`cost`", nullable = false)
	private BigDecimal cost;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "`date`", nullable = false)
	private Date date;
	
	@Column(name = "`comment`", nullable = false)
	private String comment;
	
	@ManyToOne
	@JoinColumn(name = "`fileID`", nullable = false)
	private File file;
	
	@ManyToOne
	@JoinColumn(name = "`categoryID`")
	private Category category;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
