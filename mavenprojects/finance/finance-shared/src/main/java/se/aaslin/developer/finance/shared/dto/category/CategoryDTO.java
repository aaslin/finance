package se.aaslin.developer.finance.shared.dto.category;

import java.io.Serializable;

public class CategoryDTO implements Serializable {
	
	private static final long serialVersionUID = -8052100314210470469L;
	
	private int id;
	private String name;
	private CategoryType type;
	private boolean changed;
	private boolean fromDB;
	
	public CategoryDTO() {
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
	
	public CategoryType getType() {
		return type;
	}

	public void setType(CategoryType type) {
		this.type = type;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public boolean isFromDB() {
		return fromDB;
	}

	public void setFromDB(boolean fromDB) {
		this.fromDB = fromDB;
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
		CategoryDTO other = (CategoryDTO) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
