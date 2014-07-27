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
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "`budget_record`", uniqueConstraints = { @UniqueConstraint(columnNames = { "`date`", "`categoryID`" }) })
public class BudgetRecord {

	@Id
	@Column(name = "`id`")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "`budget`", nullable = false)
	private BigDecimal budget;

	@Column(name = "`date`", nullable = false)
	private Date date;

	@ManyToOne
	@JoinColumn(name = "`categoryID`", nullable = false)
	private Category category;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getBudget() {
		return budget;
	}

	public void setBudget(BigDecimal budget) {
		this.budget = budget;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
