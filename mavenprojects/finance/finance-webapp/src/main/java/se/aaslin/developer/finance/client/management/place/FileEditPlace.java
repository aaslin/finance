package se.aaslin.developer.finance.client.management.place;

import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class FileEditPlace extends AbstractManagementPlace {
	
	private final String fileName;

	public FileEditPlace(String fileName) {
		this.fileName = fileName;
	}

	public String getToken() {
		return fileName;
	}

	@Prefix("management,file,edit")
	public static class Tokenizer implements PlaceTokenizer<FileEditPlace> {

		@Override
		public FileEditPlace getPlace(String token) {
			return new FileEditPlace(token);
		}

		@Override
		public String getToken(FileEditPlace place) {
			return place.getToken();
		}
	}
}
