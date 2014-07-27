package se.aaslin.developer.finance.client.finance.place;

import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ShowBudgetMonthPlace extends AbstractFinancePlace {

	public String getToken() {
		return "";
	}

	@Prefix("finance,budget,show,month")
	public static class Tokenizer implements PlaceTokenizer<ShowBudgetMonthPlace> {

		@Override
		public ShowBudgetMonthPlace getPlace(String token) {
			return new ShowBudgetMonthPlace();
		}

		@Override
		public String getToken(ShowBudgetMonthPlace place) {
			return place.getToken();
		}
	}
}
