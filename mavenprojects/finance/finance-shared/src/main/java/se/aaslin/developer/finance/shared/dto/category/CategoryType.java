package se.aaslin.developer.finance.shared.dto.category;

public enum CategoryType {
	EXPENSE("Expense"), INCOME("Income");

	private final String name;

	private CategoryType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public static CategoryType parse(String string) {
		for (CategoryType type : values()) {
			if (string.equals(type.getName())) {
				return type;
			}
		}
		
		return null;
	}
}