package se.aaslin.developer.finance.client.management.place;

import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class CategoryRulePlace extends AbstractManagementPlace {
	
	public String getToken() {
		return "";
	}

	@Prefix("management,category,rule")
	public static class Tokenizer implements PlaceTokenizer<CategoryRulePlace> {

		@Override
		public CategoryRulePlace getPlace(String token) {
			return new CategoryRulePlace();
		}

		@Override
		public String getToken(CategoryRulePlace place) {
			return place.getToken();
		}
	}
}
