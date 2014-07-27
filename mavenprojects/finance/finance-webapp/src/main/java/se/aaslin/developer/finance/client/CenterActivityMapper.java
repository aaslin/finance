package se.aaslin.developer.finance.client;

import se.aaslin.developer.finance.client.finance.budget.presenter.ShowBudgetAllPresenter;
import se.aaslin.developer.finance.client.finance.budget.presenter.ShowBudgetMonthPresenter;
import se.aaslin.developer.finance.client.finance.budget.service.ShowBudgetMonthService;
import se.aaslin.developer.finance.client.finance.budget.service.ShowBudgetYearService;
import se.aaslin.developer.finance.client.finance.budget.view.BudgetOutcomeView;
import se.aaslin.developer.finance.client.finance.place.ShowBudgetYearPlace;
import se.aaslin.developer.finance.client.finance.place.ShowBudgetMonthPlace;
import se.aaslin.developer.finance.client.home.place.HomePlace;
import se.aaslin.developer.finance.client.home.presenter.HomePresenter;
import se.aaslin.developer.finance.client.home.view.HomeView;
import se.aaslin.developer.finance.client.management.category.presenter.CategoryBrowsePresenter;
import se.aaslin.developer.finance.client.management.category.presenter.CategoryRulePresenter;
import se.aaslin.developer.finance.client.management.category.service.CategoryBrowseService;
import se.aaslin.developer.finance.client.management.category.service.CategoryRuleService;
import se.aaslin.developer.finance.client.management.category.view.CategoryBrowseView;
import se.aaslin.developer.finance.client.management.category.view.CategoryRuleView;
import se.aaslin.developer.finance.client.management.filehandler.presenter.FileBrowsePresenter;
import se.aaslin.developer.finance.client.management.filehandler.presenter.FileEditPresenter;
import se.aaslin.developer.finance.client.management.filehandler.presenter.FileUploadPresenter;
import se.aaslin.developer.finance.client.management.filehandler.service.FileBrowseService;
import se.aaslin.developer.finance.client.management.filehandler.service.FileEditService;
import se.aaslin.developer.finance.client.management.filehandler.service.FileUploadService;
import se.aaslin.developer.finance.client.management.filehandler.view.FileBrowseView;
import se.aaslin.developer.finance.client.management.filehandler.view.FileEditView;
import se.aaslin.developer.finance.client.management.filehandler.view.FileUploadView;
import se.aaslin.developer.finance.client.management.place.CategoryBrowsePlace;
import se.aaslin.developer.finance.client.management.place.CategoryRulePlace;
import se.aaslin.developer.finance.client.management.place.FileBrowsePlace;
import se.aaslin.developer.finance.client.management.place.FileEditPlace;
import se.aaslin.developer.finance.client.management.place.FileUploadPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;

public class CenterActivityMapper implements ActivityMapper {
	
	private ClientFactory clientFactory;
	
	public CenterActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		// Home
		if (place instanceof HomePlace) {
			return new HomePresenter(new HomeView(), clientFactory);
		}
		
		// Files
		if (place instanceof FileEditPlace) {
			return new FileEditPresenter(new FileEditView(), GWT.<FileEditService>create(FileEditService.class), (FileEditPlace) place, clientFactory.getPlaceController());
		} 
		if (place instanceof FileBrowsePlace) {
			return new FileBrowsePresenter(new FileBrowseView(), GWT.<FileBrowseService>create(FileBrowseService.class), clientFactory.getHistoryMapper());
		} 
		if (place instanceof FileUploadPlace) {
			return new FileUploadPresenter(new FileUploadView(), GWT.<FileUploadService>create(FileUploadService.class), clientFactory.getPlaceController());
		}
		
		//Budget
		if (place instanceof ShowBudgetMonthPlace) {
			return new ShowBudgetMonthPresenter(new BudgetOutcomeView(), GWT.<ShowBudgetMonthService>create(ShowBudgetMonthService.class), clientFactory);
		}
		if (place instanceof ShowBudgetYearPlace) {
			return new ShowBudgetAllPresenter(new BudgetOutcomeView(), GWT.<ShowBudgetYearService>create(ShowBudgetYearService.class), clientFactory);
		}
		
		//Category
		if (place instanceof CategoryBrowsePlace) {
			return new CategoryBrowsePresenter(new CategoryBrowseView(), GWT.<CategoryBrowseService>create(CategoryBrowseService.class));
		}
		if (place instanceof CategoryRulePlace) {
			return new CategoryRulePresenter(new CategoryRuleView(), GWT.<CategoryRuleService>create(CategoryRuleService.class));
		}

		return null;
	}
}
