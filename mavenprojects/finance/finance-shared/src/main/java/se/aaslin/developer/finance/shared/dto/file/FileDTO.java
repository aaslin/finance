package se.aaslin.developer.finance.shared.dto.file;

import java.io.Serializable;
import java.util.Date;

public class FileDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String name;
	private Date tag;
	private String token;
	
	public FileDTO() {
	}

	public FileDTO(String name, Date tag) {
		this.name = name;
		this.tag = tag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getTag() {
		return tag;
	}

	public void setTag(Date tag) {
		this.tag = tag;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
