package se.aaslin.developer.finance.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;

public class ClientFactory {
	private final PlaceController placeController;
	private final PlaceHistoryMapper historyMapper;
	
	public ClientFactory(PlaceController placeController, PlaceHistoryMapper historyMapper) {
		this.placeController = placeController;
		this.historyMapper = historyMapper;
	}
	
	public PlaceController getPlaceController() {
		return placeController;
	}
	
	public PlaceHistoryMapper getHistoryMapper() {
		return historyMapper;
	}
}
