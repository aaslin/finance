package se.aaslin.developer.finance.client.menu.presenter;

import java.util.Map;

import se.aaslin.developer.finance.client.ClientFactory;

import com.google.gwt.place.shared.Place;

public class FinanceSubMenuPresenter extends AbstractSubMenuPresenter {

	public FinanceSubMenuPresenter(View view, ClientFactory clientFactory, Map<String, Place> menuItems) {
		super(view, clientFactory, menuItems, SubMenu.FINANCE);
	}
}
