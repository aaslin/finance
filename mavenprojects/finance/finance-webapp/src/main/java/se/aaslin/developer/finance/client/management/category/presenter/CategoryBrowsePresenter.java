package se.aaslin.developer.finance.client.management.category.presenter;

import java.util.ArrayList;
import java.util.List;

import se.aaslin.developer.finance.client.AbstractCallback;
import se.aaslin.developer.finance.client.management.category.service.CategoryBrowseService;
import se.aaslin.developer.finance.shared.dto.category.CategoryDTO;
import se.aaslin.developer.finance.shared.dto.category.CategoryType;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;

public class CategoryBrowsePresenter extends AbstractActivity {
	public interface View extends IsWidget {

		void addCategories(List<CategoryDTO> categories);

		Button getSaveButton();
		
		Button getResetButton();
		
		Button getAddButton();

		void setupTable(RemoveCallback removeCallback, UpdateCallback updateCallback);
	}

	public interface RemoveCallback {
		
		void onRemove(CategoryDTO category);
	}
	
	public interface UpdateCallback {
		
		void onUpdate();
	}
	
	private final View view;
	private final  CategoryBrowseService service;
	final List<CategoryDTO> categories = new ArrayList<CategoryDTO>();
	
	public CategoryBrowsePresenter(View view, CategoryBrowseService service) {
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
				getCategories();
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
		
		view.setupTable(new RemoveCallback() {
			
			@Override
			public void onRemove(CategoryDTO category) {
				remove(category);
			}
		}, new UpdateCallback() {
			
			@Override
			public void onUpdate() {
				view.getSaveButton().setEnabled(true);
				view.getResetButton().setEnabled(true);
			}
		});
	}

	private void getCategories() {
		service.getCategories(new AbstractCallback<List<CategoryDTO>>() {

			@Override
			public void onSuccess(List<CategoryDTO> response) {
				categories.clear();
				categories.addAll(response);
				updateView();
			}
		});
	}

	private void updateView() {
		view.getSaveButton().setEnabled(false);
		view.getResetButton().setEnabled(false);
		view.addCategories(categories);
	}

	private void saveCategories() {
		List<CategoryDTO> changed = new ArrayList<CategoryDTO>();
		for (CategoryDTO category : categories) {
			if (category.isChanged()) {
				changed.add(category);
			}
		}
		if (changed.size() > 0) {
			service.saveCategories(changed, new AbstractCallback<List<CategoryDTO>>() {

				@Override
				protected void onSuccess(List<CategoryDTO> response) {
					categories.clear();
					categories.addAll(response);
					updateView();
				}
			});
		}
	}

	private void remove(final CategoryDTO category) {
		if (Window.confirm("Are you sure you want to delete this category?")) {
			if (!category.isFromDB()) {
				categories.remove(category);
				updateView();
			} else {
				service.removeCategory(category, new AbstractCallback<Void>() {
		
					@Override
					protected void onSuccess(Void response) {
						categories.remove(category);
						updateView();
					}
				});
			}
		}
	}

	private void addCategory() {
		CategoryDTO category = new CategoryDTO();
		category.setFromDB(false);
		category.setChanged(true);
		category.setName("New category");
		category.setType(CategoryType.EXPENSE);
		categories.add(category);
		updateView();
	}
}
