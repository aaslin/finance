package se.aaslin.developer.finance.client.menu.presenter;

import java.util.Map;

import se.aaslin.developer.finance.client.ClientFactory;

import com.google.gwt.place.shared.Place;

public class ManagementSubMenuPresenter extends AbstractSubMenuPresenter {

	public ManagementSubMenuPresenter(View view, ClientFactory clientFactory, Map<String, Place> menuItems) {
		super(view, clientFactory, menuItems, SubMenu.MANAGEMENT);
	}
}
