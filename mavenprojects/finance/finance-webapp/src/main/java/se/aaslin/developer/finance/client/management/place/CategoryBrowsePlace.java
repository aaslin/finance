package se.aaslin.developer.finance.client.management.place;

import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class CategoryBrowsePlace extends AbstractManagementPlace {
	
	public String getToken() {
		return "";
	}

	@Prefix("management,category,browse")
	public static class Tokenizer implements PlaceTokenizer<CategoryBrowsePlace> {

		@Override
		public CategoryBrowsePlace getPlace(String token) {
			return new CategoryBrowsePlace();
		}

		@Override
		public String getToken(CategoryBrowsePlace place) {
			return place.getToken();
		}
	}
}
