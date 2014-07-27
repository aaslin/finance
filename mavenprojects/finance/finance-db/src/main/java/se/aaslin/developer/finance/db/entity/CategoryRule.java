package se.aaslin.developer.finance.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import se.aaslin.developer.finance.shared.dto.category.CategoryRuleOperator;

@Entity
@Table(name = "category_rule")
public class CategoryRule {

	@Id
	@Column(name = "`id`")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "`name`", nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "`categoryID`", nullable = false)
	private Category category;

	@Column(name = "`operator`", nullable = false)
	@Enumerated(EnumType.STRING)
	private CategoryRuleOperator operator;

	@Column(name = "`pattern`", nullable = false)
	private String pattern;

	@Column(name = "`enabled`")
	private boolean enabled;

	@PrePersist
	public void prePersist() {
		enabled = true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public CategoryRuleOperator getOperator() {
		return operator;
	}

	public void setOperator(CategoryRuleOperator operator) {
		this.operator = operator;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "CategoryRule [name=" + name + "]";
	}
}
