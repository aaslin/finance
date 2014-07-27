package se.aaslin.developer.finance.client;

import java.util.LinkedHashMap;
import java.util.Map;

import se.aaslin.developer.finance.client.account.place.AbstractAccountPlace;
import se.aaslin.developer.finance.client.finance.place.AbstractFinancePlace;
import se.aaslin.developer.finance.client.finance.place.ShowBudgetYearPlace;
import se.aaslin.developer.finance.client.finance.place.ShowBudgetMonthPlace;
import se.aaslin.developer.finance.client.management.place.AbstractManagementPlace;
import se.aaslin.developer.finance.client.management.place.CategoryBrowsePlace;
import se.aaslin.developer.finance.client.management.place.CategoryRulePlace;
import se.aaslin.developer.finance.client.management.place.FileBrowsePlace;
import se.aaslin.developer.finance.client.management.place.FileUploadPlace;
import se.aaslin.developer.finance.client.menu.presenter.AccountSubMenuPresenter;
import se.aaslin.developer.finance.client.menu.presenter.FinanceSubMenuPresenter;
import se.aaslin.developer.finance.client.menu.presenter.ManagementSubMenuPresenter;
import se.aaslin.developer.finance.client.menu.view.SubMenuView;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class SubMenuActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;
	
	public SubMenuActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof AbstractFinancePlace) {
			Map<String, Place> menuItems = new LinkedHashMap<String, Place>();
			menuItems.put("Budget month", new ShowBudgetMonthPlace());
			menuItems.put("Budget year", new ShowBudgetYearPlace());
			
			return new FinanceSubMenuPresenter(new SubMenuView(), clientFactory, menuItems);
		}
		
		if (place instanceof AbstractManagementPlace) {
			Map<String, Place> menuItems = new LinkedHashMap<String, Place>();
			menuItems.put("Files", new FileBrowsePlace());
			menuItems.put("Upload file", new FileUploadPlace());
			menuItems.put("Categories", new CategoryBrowsePlace());
			menuItems.put("Rules", new CategoryRulePlace());
			
			return new ManagementSubMenuPresenter(new SubMenuView(), clientFactory, menuItems);	
		}
		
		if (place instanceof AbstractAccountPlace) {
			Map<String, Place> menuItems = new LinkedHashMap<String, Place>();

			return new AccountSubMenuPresenter(new SubMenuView(), clientFactory, menuItems);
		}
			
		return null;
	}
}