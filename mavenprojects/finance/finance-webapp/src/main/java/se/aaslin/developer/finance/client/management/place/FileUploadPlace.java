package se.aaslin.developer.finance.client.management.place;

import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class FileUploadPlace extends AbstractManagementPlace {
	
	private final String token = "";

	public String getToken() {
		return token;
	}

	@Prefix("management,file,upload")
	public static class Tokenizer implements PlaceTokenizer<FileUploadPlace> {

		@Override
		public FileUploadPlace getPlace(String token) {
			return new FileUploadPlace();
		}

		@Override
		public String getToken(FileUploadPlace place) {
			return place.getToken();
		}
	}
}
