package se.aaslin.developer.finance.client.management.category.presenter;

import java.util.ArrayList;
import java.util.List;

import se.aaslin.developer.finance.client.AbstractCallback;
import se.aaslin.developer.finance.client.management.category.service.CategoryRuleService;
import se.aaslin.developer.finance.shared.dto.category.CategoryDTO;
import se.aaslin.developer.finance.shared.dto.category.CategoryRuleDTO;
import se.aaslin.developer.finance.shared.dto.category.CategoryRuleOperator;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;

public class CategoryRulePresenter extends AbstractActivity {
	public interface View extends IsWidget {

		Button getSaveButton();
		
		Button getResetButton();
		
		Button getAddButton();

		void setupTable(RemoveCallback removeCallback, UpdateCallback updateCallback, List<CategoryDTO> categories);

		void addCategoryRules(List<CategoryRuleDTO> rules);
	}

	public interface RemoveCallback {
		
		void onRemove(CategoryRuleDTO rule);
	}
	
	public interface UpdateCallback {
		
		void onUpdate();
	}
	
	private final View view;
	private final CategoryRuleService service;
	private final List<CategoryDTO> categories = new ArrayList<CategoryDTO>();
	private final List<CategoryRuleDTO> categoryRules = new ArrayList<CategoryRuleDTO>();
	
	public CategoryRulePresenter(View view, CategoryRuleService service) {
		this.view = view;
		this.service = service;
		
		bind();
		getCategories();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view);
	}

	private void bind() {
		view.getResetButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getRules();
			}
		});
		
		view.getSaveButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				saveCategories();
			}
		});
		
		view.getAddButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				addCategory();
			}
		});
		
		
	}
	
	private void getCategories() {
		service.getCategories(new AbstractCallback<List<CategoryDTO>>() {

			@Override
			public void onSuccess(List<CategoryDTO> response) {
				categories.clear();
				categories.addAll(response);
				view.setupTable(new RemoveCallback() {
					
					@Override
					public void onRemove(CategoryRuleDTO rule) {
						remove(rule);
					}
				}, new UpdateCallback() {
					
					@Override
					public void onUpdate() {
						view.getSaveButton().setEnabled(true);
						view.getResetButton().setEnabled(true);
					}
				}, categories);
				
				getRules();
			}
		});
	}

	private void getRules() {
		service.getCategoryRules(new AbstractCallback<List<CategoryRuleDTO>>() {

			@Override
			public void onSuccess(List<CategoryRuleDTO> response) {
				categoryRules.clear();
				categoryRules.addAll(response);
				updateView();
			}
		});
	}

	private void updateView() {
		view.getSaveButton().setEnabled(false);
		view.getResetButton().setEnabled(false);
		view.addCategoryRules(categoryRules);
	}

	private void saveCategories() {
		List<CategoryRuleDTO> changed = new ArrayList<CategoryRuleDTO>();
		for (CategoryRuleDTO category : categoryRules) {
			if (category.isChanged()) {
				changed.add(category);
			}
		}
		if (changed.size() > 0) {
			service.saveCategoryRule(changed, new AbstractCallback<List<CategoryRuleDTO>>() {

				@Override
				protected void onSuccess(List<CategoryRuleDTO> response) {
					categoryRules.clear();
					categoryRules.addAll(response);
					updateView();
				}
			});
		}
	}

	private void remove(final CategoryRuleDTO rule) {
		if (Window.confirm("Are you sure you want to delete this category?")) {
			if (!rule.isFromDB()) {
				categoryRules.remove(rule);
				updateView();
			} else {
				service.removeCategoryRule(rule, new AbstractCallback<Void>() {
		
					@Override
					protected void onSuccess(Void response) {
						categoryRules.remove(rule);
						updateView();
					}
				});
			}
		}
	}

	private void addCategory() {
		CategoryRuleDTO rule = new CategoryRuleDTO();
		rule.setCategory(categories.get(0));
		rule.setChanged(true);
		rule.setEnabled(false);
		rule.setFromDB(false);
		rule.setName("New rule");
		rule.setOperator(CategoryRuleOperator.values()[0]);
		rule.setPattern("pattern");
		categoryRules.add(rule);
		updateView();
	}
}
