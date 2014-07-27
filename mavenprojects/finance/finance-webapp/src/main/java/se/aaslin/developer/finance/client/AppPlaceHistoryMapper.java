package se.aaslin.developer.finance.client;

import se.aaslin.developer.finance.client.account.place.UserSettingsPlace;
import se.aaslin.developer.finance.client.finance.place.ShowBudgetYearPlace;
import se.aaslin.developer.finance.client.finance.place.ShowBudgetMonthPlace;
import se.aaslin.developer.finance.client.home.place.HomePlace;
import se.aaslin.developer.finance.client.management.place.CategoryBrowsePlace;
import se.aaslin.developer.finance.client.management.place.CategoryRulePlace;
import se.aaslin.developer.finance.client.management.place.FileBrowsePlace;
import se.aaslin.developer.finance.client.management.place.FileEditPlace;
import se.aaslin.developer.finance.client.management.place.FileUploadPlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({
	// Home
	HomePlace.Tokenizer.class, 

	// Finance
	ShowBudgetMonthPlace.Tokenizer.class,
	ShowBudgetYearPlace.Tokenizer.class,
	
	// Management
	FileBrowsePlace.Tokenizer.class, 
	FileEditPlace.Tokenizer.class, 
	FileUploadPlace.Tokenizer.class,
	CategoryBrowsePlace.Tokenizer.class,
	CategoryRulePlace.Tokenizer.class,
	
	// Account
	UserSettingsPlace.Tokenizer.class})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
}
