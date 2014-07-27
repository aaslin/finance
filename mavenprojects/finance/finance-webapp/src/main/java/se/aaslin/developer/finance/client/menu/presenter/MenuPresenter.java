package se.aaslin.developer.finance.client.menu.presenter;

import se.aaslin.developer.finance.client.account.place.AbstractAccountPlace;
import se.aaslin.developer.finance.client.account.place.UserSettingsPlace;
import se.aaslin.developer.finance.client.finance.place.AbstractFinancePlace;
import se.aaslin.developer.finance.client.finance.place.ShowBudgetMonthPlace;
import se.aaslin.developer.finance.client.home.place.HomePlace;
import se.aaslin.developer.finance.client.management.place.AbstractManagementPlace;
import se.aaslin.developer.finance.client.management.place.FileBrowsePlace;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

public class MenuPresenter extends AbstractActivity {
	public interface View extends IsWidget {
		
		void addMenu(String name, String histotyToken, boolean isActive);
	}
	
	View view;
	Place place;
	PlaceHistoryMapper historyMapper;
	
	public MenuPresenter(View view, Place place, PlaceHistoryMapper historyMapper) {
		this.view = view;
		this.place = place;
		this.historyMapper = historyMapper;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		bind();
	}

	private void bind() {
		view.addMenu("Home", historyMapper.getToken(new HomePlace()), (place instanceof HomePlace));
		view.addMenu("Finance", historyMapper.getToken(new ShowBudgetMonthPlace()), (place instanceof AbstractFinancePlace));
		view.addMenu("Management", historyMapper.getToken(new FileBrowsePlace()), (place instanceof AbstractManagementPlace));
		view.addMenu("Account", historyMapper.getToken(new UserSettingsPlace()), (place instanceof AbstractAccountPlace));
	}
}
