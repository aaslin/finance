package se.aaslin.developer.finance.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import se.aaslin.developer.finance.shared.dto.category.CategoryType;

@Entity
@Table(name = "`category`")
public class Category {
	
	@Id
	@Column(name = "`id`")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "`name`")
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private CategoryType type; 
	
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

	public CategoryType getType() {
		return type;
	}

	public void setType(CategoryType type) {
		this.type = type;
	}
}
