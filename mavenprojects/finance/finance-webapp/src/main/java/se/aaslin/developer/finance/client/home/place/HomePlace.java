package se.aaslin.developer.finance.client.home.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class HomePlace extends Place {
	
	private final String token;

	public HomePlace() {
		this("");
	}

	public HomePlace(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	@Prefix("home")
	public static class Tokenizer implements PlaceTokenizer<HomePlace> {

		public HomePlace getPlace(String token) {
			return new HomePlace(token);
		}

		public String getToken(HomePlace place) {
			return place.getToken();
		}
	}
}
