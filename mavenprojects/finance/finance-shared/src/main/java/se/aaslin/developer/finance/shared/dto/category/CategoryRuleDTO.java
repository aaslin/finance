package se.aaslin.developer.finance.shared.dto.category;

import java.io.Serializable;

public class CategoryRuleDTO implements Serializable{
	
	private static final long serialVersionUID = -8062090221178602003L;
	
	private int id;
	private String name;
	private CategoryRuleOperator operator;
	private String pattern;
	private CategoryDTO category;
	private boolean enabled;
	private boolean fromDB;
	private boolean changed;
	
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
	public CategoryDTO getCategory() {
		return category;
	}
	public void setCategory(CategoryDTO category) {
		this.category = category;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
}
