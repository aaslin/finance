package se.aaslin.developer.finance.client.home.presenter;

import se.aaslin.developer.finance.client.ClientFactory;
import se.aaslin.developer.finance.client.account.place.UserSettingsPlace;
import se.aaslin.developer.finance.client.finance.place.ShowBudgetMonthPlace;
import se.aaslin.developer.finance.client.management.place.FileBrowsePlace;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.IsWidget;

public class HomePresenter extends AbstractActivity {
	public interface View extends IsWidget {

		Hyperlink getFinance();
		
		Hyperlink getManagement();
		
		Hyperlink getAccount();
	}
	
	View view;
	ClientFactory clientFactory;
	
	public HomePresenter(View view, ClientFactory clientFactory) {
		this.view = view;
		this.clientFactory = clientFactory;
		setupLinks();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view);
	}

	private void setupLinks() {
		view.getFinance().setTargetHistoryToken(clientFactory.getHistoryMapper().getToken(new ShowBudgetMonthPlace()));
		view.getManagement().setTargetHistoryToken(clientFactory.getHistoryMapper().getToken(new FileBrowsePlace()));
		view.getAccount().setTargetHistoryToken(clientFactory.getHistoryMapper().getToken(new UserSettingsPlace()));
	}
}
