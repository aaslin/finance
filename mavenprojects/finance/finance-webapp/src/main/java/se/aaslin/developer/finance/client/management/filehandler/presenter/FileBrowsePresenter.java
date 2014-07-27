package se.aaslin.developer.finance.client.management.filehandler.presenter;

import java.util.ArrayList;
import java.util.List;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import se.aaslin.developer.finance.client.management.filehandler.service.FileBrowseService;
import se.aaslin.developer.finance.client.management.place.FileEditPlace;
import se.aaslin.developer.finance.shared.dto.file.FileDTO;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

public class FileBrowsePresenter extends AbstractActivity {
	public interface View extends IsWidget {

		void addFiles(List<FileDTO> files);

		void setupTable(FieldUpdateCallback fieldUpdateCallback);
	}

	public interface FieldUpdateCallback {
		
		void onUpdate(FileDTO file);
		
		void onRemove(FileDTO file);
	}
	
	View view;
	PlaceHistoryMapper historyMapper;
	FileBrowseService service;
	final List<FileDTO> files = new ArrayList<FileDTO>();
	
	public FileBrowsePresenter(View view, FileBrowseService service, PlaceHistoryMapper historyMapper) {
		this.view = view;
		this.service = service;
		this.historyMapper = historyMapper;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view);
		bind();
		view.setupTable(new FieldUpdateCallback() {
			
			@Override
			public void onUpdate(FileDTO file) {
				update(file);
			}
			
			@Override
			public void onRemove(FileDTO file) {
				remove(file);
			}
		});
		getFiles();
	}

	private void bind() {

	}

	private void getFiles() {
		service.getFiles(new MethodCallback<List<FileDTO>>() {

			@Override
			public void onSuccess(Method method, List<FileDTO> response) {
				files.clear();
				files.addAll(response);
				updateView();
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				Window.alert(exception.getMessage());
			}
		});
	}

	private void updateView() {
		for (FileDTO fileDTO : files) {
			String fileName = fileDTO.getName();
			String token = historyMapper.getToken(new FileEditPlace(fileName));
			fileDTO.setToken(token);
		}
		view.addFiles(files);
	}

	private void update(FileDTO file) {
		service.update(file, new MethodCallback<Void>() {
			
			@Override
			public void onSuccess(Method method, Void response) {
			}
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				Window.alert(exception.getMessage());
			}
		});
	}

	private void remove(FileDTO file) {
		if (Window.confirm("Are you sure you want to delete this file?")) {
			service.remove(file, new MethodCallback<List<FileDTO>>() {
				
				@Override
				public void onSuccess(Method method, List<FileDTO> response) {
					files.clear();
					files.addAll(response);
					updateView();
				}
				
				@Override
				public void onFailure(Method method, Throwable exception) {
					Window.alert(exception.getMessage());
				}
			});
		}
	}
}
