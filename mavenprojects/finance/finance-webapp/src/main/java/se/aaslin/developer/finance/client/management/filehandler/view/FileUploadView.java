package se.aaslin.developer.finance.client.management.filehandler.view;

import java.util.Date;

import se.aaslin.developer.finance.client.common.widget.HiddenInput;
import se.aaslin.developer.finance.client.management.filehandler.presenter.FileUploadPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

public class FileUploadView extends Composite implements FileUploadPresenter.View {
	public interface HomeViewUIBinder extends UiBinder<HTMLPanel, FileUploadView> {
	}

	HomeViewUIBinder uiBinder = GWT.create(HomeViewUIBinder.class);

	@UiField FormPanel uploadForm;
	@UiField FileUpload fileUpload;
	@UiField Button uploadButton;
	@UiField VerticalPanel datePanel;
	@UiField HiddenInput year;
	@UiField HiddenInput month;
	@UiField Label message;
	
	private DateBox dateBox;
	
	public FileUploadView() {
		initWidget(uiBinder.createAndBindUi(this));
		dateBox = new DateBox();
	    dateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy MMMM")));
	    dateBox.setValue(new Date());
	    datePanel.add(dateBox);
	}

	@Override
	public FormPanel getUploadForm() {
		return uploadForm;
	}

	@Override
	public FileUpload getFileUpload() {
		return fileUpload;
	}

	@Override
	public void showLoadingSpinner() {
		
	}

	@Override
	public void hideLoadingSpinner() {
		
	}

	@Override
	public Button getUploadButton() {
		return uploadButton;
	}
	
	@Override
	public DateBox getDateBox() {
		return dateBox;
	}
	
	@Override
	public void setFormInput(int year, int month) {
		this.year.setValue(Integer.toString(year));
		this.month.setValue(Integer.toString(month));
	}

	@Override
	public void showFormMessage(String text) {
		message.setText(text);
		message.setVisible(true);
	}

	@Override
	public void hideFormMessage() {
		message.setVisible(false);
	}
}
