package se.aaslin.developer.finance.shared.dto;

import java.io.Serializable;

public class TransactionDTO implements Serializable {

	private static final long serialVersionUID = 929165481281326523L;

	private int id;

	public TransactionDTO(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
