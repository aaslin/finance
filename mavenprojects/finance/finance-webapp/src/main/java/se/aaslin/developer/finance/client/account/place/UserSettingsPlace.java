package se.aaslin.developer.finance.client.account.place;

import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class UserSettingsPlace extends AbstractAccountPlace {

	public UserSettingsPlace() {
	}

	@Prefix("account,user,settings")
	public static class Tokenizer implements PlaceTokenizer<UserSettingsPlace> {

		public UserSettingsPlace getPlace(String token) {
			return new UserSettingsPlace();
		}

		public String getToken(UserSettingsPlace place) {
			return "";
		}
	}
}
