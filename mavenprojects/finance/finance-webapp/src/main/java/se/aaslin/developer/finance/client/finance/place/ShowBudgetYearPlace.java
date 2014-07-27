package se.aaslin.developer.finance.client.finance.place;

import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ShowBudgetYearPlace extends AbstractFinancePlace {

	public String getToken() {
		return "";
	}

	@Prefix("finance,budget,show,year")
	public static class Tokenizer implements PlaceTokenizer<ShowBudgetYearPlace> {

		@Override
		public ShowBudgetYearPlace getPlace(String token) {
			return new ShowBudgetYearPlace();
		}

		@Override
		public String getToken(ShowBudgetYearPlace place) {
			return place.getToken();
		}
	}
}
