package se.aaslin.developer.finance.client.management.place;

import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class FileBrowsePlace extends AbstractManagementPlace {

	private final String token = "";

	public String getToken() {
		return token;
	}

	@Prefix("management,file,browse")
	public static class Tokenizer implements PlaceTokenizer<FileBrowsePlace> {

		@Override
		public FileBrowsePlace getPlace(String token) {
			return new FileBrowsePlace();
		}

		@Override
		public String getToken(FileBrowsePlace place) {
			return place.getToken();
		}
	}
}
