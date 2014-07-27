package se.aaslin.developer.finance.server.backend.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum representing error messages.
 * 
 * @author samuelwirzen
 *
 */
public enum ErrorMessage {

	DATABASE_UNAVAILABLE("1"), ILLEGAL_APPLICATION_STATE("2"), MISSING_DATA("3"), WRONG_DATA_FORMAT("4"), WRITE_TO_DISK_ERROR("5"), READ_FILE_ERROR("6"), ILLEGAL_ARGUMENT(
			"7"), WRONG_DATA_TYPE("8");

	private static final Map<String, ErrorMessage> lookup = new HashMap<String, ErrorMessage>();

	static {
		for (ErrorMessage a : EnumSet.allOf(ErrorMessage.class)) {
			lookup.put(a.getCaption(), a);
		}
	}

	private String caption;

	private ErrorMessage(String caption) {
		this.caption = caption;
	}

	public String getCaption() {
		return caption;
	}

	public static ErrorMessage get(String caption) {
		return lookup.get(caption);
	}

}
