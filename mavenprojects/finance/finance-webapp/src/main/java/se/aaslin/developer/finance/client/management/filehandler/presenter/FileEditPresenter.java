package se.aaslin.developer.finance.client.management.filehandler.presenter;

import java.util.ArrayList;
import java.util.List;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import se.aaslin.developer.finance.client.AbstractCallback;
import se.aaslin.developer.finance.client.management.filehandler.service.FileEditService;
import se.aaslin.developer.finance.client.management.place.FileBrowsePlace;
import se.aaslin.developer.finance.client.management.place.FileEditPlace;
import se.aaslin.developer.finance.shared.dto.category.CategoryDTO;
import se.aaslin.developer.finance.shared.dto.file.FileData;
import se.aaslin.developer.finance.shared.dto.file.FileDataset;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;

public class FileEditPresenter extends AbstractActivity {
	public interface View extends IsWidget {

		String CHOOSE_CATEGORY = "-- Choose category --";

		void setFilename(String filename);

		void setErrorMsg(String msg);

		void setRows(List<FileDataset> rows);

		Button getSaveButton();
		
		Button getResetButton();

		void setupTable(List<String> categories, SelectionCallback selectionCallback, RemoveCallback removeCallback);
	}

	public interface SelectionCallback {

		void onChange(FileDataset ds, String categoryName);
	}
	
	public interface RemoveCallback {
		
		void onRemove(FileDataset ds);
	}

	public interface UpdateCallback {
		
		void onCostUpdate(FileDataset ds, String value);
	}
	
	View view;
	FileEditService srv;
	FileEditPlace place;
	PlaceController placeController;

	List<CategoryDTO> categories;
	FileData fileData;

	public FileEditPresenter(View view, FileEditService srv, FileEditPlace place, PlaceController placeController) {
		this.view = view;
		this.srv = srv;
		this.place = place;
		this.placeController = placeController;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		bind();
		fetchCategories();
		panel.setWidget(view);
	}

	private void bind() {
		view.getSaveButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				srv.persistFile(fileData, new MethodCallback<Void>() {

					@Override
					public void onSuccess(Method method, Void response) {
						placeController.goTo(new FileBrowsePlace());
					}
					
					@Override
					public void onFailure(Method method, Throwable exception) {
						Window.alert(exception.getMessage());		
					}
				});
			}
		});
		
		view.getResetButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				fetchFile();
			}
		});
	}

	private void fetchCategories() {
		srv.getCategories(new MethodCallback<List<CategoryDTO>>() {
			
			@Override
			public void onSuccess(Method method, List<CategoryDTO> response) {
				categories = response;
				setupTable();
				fetchFile();
			}
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				Window.alert(exception.getMessage());
			}
		});
	}

	private void fetchFile() {
		srv.getFile(place.getToken(), new MethodCallback<FileData>() {

			@Override
			public void onSuccess(Method method, FileData response) {
				view.getResetButton().setEnabled(false);
				view.getSaveButton().setEnabled(false);
				fileData = response;
				updateTable();
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				view.setErrorMsg("File not found");
			}
		});
	}

	private List<String> getCategories() {
		List<String> categoryStrings = new ArrayList<String>();
		for (CategoryDTO categoryDTO : categories) {
			categoryStrings.add(categoryDTO.getName());
		}
		
		return categoryStrings;
	}

	private void setupTable() {
		view.setupTable(getCategories(), new SelectionCallback() {

			@Override
			public void onChange(FileDataset ds, String categoryName) {
				ds.setCategory(categoryName);
				ds.setChanged(true);
				view.getResetButton().setEnabled(true);
				checkCategorizing();
			}
		}, new RemoveCallback() {
			
			@Override
			public void onRemove(FileDataset ds) {
				removeTransaction(ds);
			}
		});
	}

	private void updateTable() {
		view.setFilename(fileData.getFileName());
		view.setRows(fileData.getFileDataset());
	}
	
	private void checkCategorizing() {
		for (FileDataset fileDataset : fileData.getFileDataset()) {
			if (fileDataset.getCategory() == null || fileDataset.getCategory().equals(View.CHOOSE_CATEGORY)) {
				view.getSaveButton().setEnabled(false);
				return;
			}
		}
		
		view.getSaveButton().setEnabled(true);
	}

	private void removeTransaction(final FileDataset ds) {
		srv.removeTransaction(ds.getId(), new AbstractCallback<Void>() {

			@Override
			public void onSuccess(Void response) {
				fileData.getFileDataset().remove(ds);
				updateTable();
			}
		});
	}
}
