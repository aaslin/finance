package se.aaslin.developer.finance.client.menu.presenter;

import java.util.Map;
import java.util.Map.Entry;

import se.aaslin.developer.finance.client.ClientFactory;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

public abstract class AbstractSubMenuPresenter extends AbstractActivity {
	public interface View extends IsWidget {
		
		void addMenu(String name, String token, boolean isActive);

		void setMenuTitle(SubMenu subMenu);
	}

	public enum SubMenu {
		FINANCE, MANAGEMENT, ACCOUNT;
	}
	
	final View view;
	final ClientFactory clientFactory;
	final Map<String, Place> menuItems;
	
	public AbstractSubMenuPresenter(View view, ClientFactory clientFactory, Map<String, Place> menuItems, SubMenu subMenu) {
		this.view = view;
		this.clientFactory = clientFactory;
		this.menuItems = menuItems;
		
		view.setMenuTitle(subMenu);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		Place currentPlace = clientFactory.getPlaceController().getWhere();
		for (Entry<String, Place> menuItem : menuItems.entrySet()) {
			view.addMenu(menuItem.getKey(), 
					clientFactory.getHistoryMapper().getToken(menuItem.getValue()), 
					menuItem.getValue().getClass().equals(currentPlace.getClass()));
		}
		panel.setWidget(view);
	}
}
