package se.aaslin.developer.finance.client;

import se.aaslin.developer.finance.client.menu.presenter.MenuPresenter;
import se.aaslin.developer.finance.client.menu.view.MenuView;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class MenuActivityMapper implements ActivityMapper {

	private AppPlaceHistoryMapper historyMapper;
	
	public MenuActivityMapper(AppPlaceHistoryMapper historyMapper) {
		this.historyMapper = historyMapper;
	}

	@Override
	public Activity getActivity(Place place) {
		MenuView view = new MenuView();
		return new MenuPresenter(view, place, historyMapper);
	}
}