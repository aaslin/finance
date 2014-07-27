package se.aaslin.developer.finance.client.management.filehandler.presenter;

import java.util.Date;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import se.aaslin.developer.finance.client.management.filehandler.service.FileUploadService;
import se.aaslin.developer.finance.client.management.place.FileEditPlace;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.datepicker.client.DateBox;

public class FileUploadPresenter extends AbstractActivity {
	
	public interface View extends IsWidget {

		FormPanel getUploadForm();

		FileUpload getFileUpload();

		void showLoadingSpinner();

		void hideLoadingSpinner();
		
		Button getUploadButton();

		DateBox getDateBox();

		void setFormInput(int year, int month);
		
		void showFormMessage(String text);
		
		void hideFormMessage();
	}
	
	View view;
	FileUploadService service;
	PlaceHistoryMapper placeHistoryMapper;
	String fileName = "";
	PlaceController placeController;
	
	public FileUploadPresenter(View view, FileUploadService service, PlaceController placeController) {
		this.view = view;
		this.service = service;
		this.placeController = placeController;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view);	
		setupFileUploadForm();
		bind();
	}

	private void setupFileUploadForm() {
		view.getUploadForm().setAction(GWT.getModuleBaseURL() + "fileUpload");
		view.getUploadForm().setMethod(FormPanel.METHOD_POST);
		view.getUploadForm().setEncoding(FormPanel.ENCODING_MULTIPART);
	}

	@SuppressWarnings("deprecation")
	private void bind() {
		view.getUploadButton().setEnabled(false);
		view.setFormInput(new Date().getYear() + 1900, new Date().getMonth());
		view.getFileUpload().addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				if (view.getFileUpload().getFilename() != null && view.getFileUpload().getFilename().length() > 0) {
					fileName = view.getFileUpload().getFilename();
					if (fileName.contains("\\")) {
						fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
					}
					service.doFileExist(fileName, new MethodCallback<Boolean>() {
						
						@Override
						public void onSuccess(Method method, Boolean response) {
							if (!response) {
								view.hideFormMessage();
								view.getUploadButton().setEnabled(true);
							} else {
								view.showFormMessage("File already exists.");
							}
						}
						
						@Override
						public void onFailure(Method method, Throwable exception) {
							Window.alert(exception.getMessage());
						}
					});
				} else {
					view.showFormMessage("File type must be .xslx.");
				}
			}
		});

		view.getUploadForm().addSubmitCompleteHandler(new SubmitCompleteHandler() {

			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				view.hideLoadingSpinner();
				placeController.goTo(new FileEditPlace(fileName));
			}
		});

		view.getUploadForm().addSubmitHandler(new SubmitHandler() {

			@Override
			public void onSubmit(SubmitEvent event) {
				if (!view.getFileUpload().getFilename().endsWith(".xlsx")) {
					view.hideLoadingSpinner();
					view.getUploadButton().setEnabled(false);
					event.cancel();
					view.showFormMessage("File type must be .xslx.");
				}
			}
		});
		
		view.getUploadButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.getUploadForm().submit();
			}
		});
		
		view.getDateBox().addValueChangeHandler(new ValueChangeHandler<Date>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				view.setFormInput(event.getValue().getYear() + 1900, event.getValue().getMonth());
			}
		});
	}
}
