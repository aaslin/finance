package se.aaslin.developer.finance.client.home.view;

import se.aaslin.developer.finance.client.home.presenter.HomePresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hyperlink;

public class HomeView extends Composite implements HomePresenter.View {
	public interface HomeViewUIBinder extends UiBinder<HTMLPanel, HomeView> {
	}

	HomeViewUIBinder uiBinder = GWT.create(HomeViewUIBinder.class);

	@UiField Hyperlink finance;
	@UiField Hyperlink management;
	@UiField Hyperlink account;
	
	public HomeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Hyperlink getFinance() {
		return finance;
	}

	@Override
	public Hyperlink getManagement() {
		return management;
	}

	@Override
	public Hyperlink getAccount() {
		return account;
	}
}
