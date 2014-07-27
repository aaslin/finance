package se.aaslin.developer.finance.client;

import org.fusesource.restygwt.client.Defaults;

import se.aaslin.developer.finance.client.home.place.HomePlace;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class AppEntryPoint extends Composite implements EntryPoint {

	private static final String GWT_PLACEHOLDER = "gwt_placeHolder";

	@UiTemplate("SiteLayoutView.ui.xml")
    public interface SiteLayoutViewUIBinder extends UiBinder<HTMLPanel, AppEntryPoint> {
    }

	SiteLayoutViewUIBinder uiBinder = GWT.create(SiteLayoutViewUIBinder.class);

    @UiField SimplePanel menu;
    @UiField SimplePanel left;
    @UiField SimplePanel center;

    private Place defaultPlace = new HomePlace("");
	
	public void onModuleLoad() {
		initWidget(uiBinder.createAndBindUi(this));
		// Workaround for parsing dates
		Defaults.setDateFormat("yyyy-MM-dd");
		
		EventBus eventBus = GWT.create(SimpleEventBus.class);
		PlaceController placeController = new PlaceController(eventBus);
		AppPlaceHistoryMapper placeHistoryMapper = GWT.create(AppPlaceHistoryMapper.class);

        PlaceHistoryHandler placeHistoryHandler = new PlaceHistoryHandler(placeHistoryMapper);
        placeHistoryHandler.register(placeController, eventBus, defaultPlace);

        ClientFactory clientFactory =  new ClientFactory(placeController, placeHistoryMapper);
        
		ActivityMapper menuActivityMapper = new MenuActivityMapper(placeHistoryMapper);
		ActivityMapper subMenuActivityMapper = new SubMenuActivityMapper(clientFactory);
		ActivityMapper centerActivityMapper = new CenterActivityMapper(clientFactory);
		
		ActivityManager menuActivityManager = new ActivityManager(menuActivityMapper, eventBus);
		ActivityManager subMenuActivityManager = new ActivityManager(subMenuActivityMapper, eventBus);
		ActivityManager centerActivityManager = new ActivityManager(centerActivityMapper, eventBus);
		
		menuActivityManager.setDisplay(menu);
		subMenuActivityManager.setDisplay(left);
		centerActivityManager.setDisplay(center);

        RootPanel.get(GWT_PLACEHOLDER).add(this);
        placeHistoryHandler.handleCurrentHistory();
	}
}
