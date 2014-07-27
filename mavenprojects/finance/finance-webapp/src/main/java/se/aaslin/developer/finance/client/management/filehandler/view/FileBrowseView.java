package se.aaslin.developer.finance.client.management.filehandler.view;

import java.util.Date;
import java.util.List;

import se.aaslin.developer.finance.client.common.widget.CRUDTable;
import se.aaslin.developer.finance.client.common.widget.CRUDTable.Column;
import se.aaslin.developer.finance.client.management.filehandler.presenter.FileBrowsePresenter;
import se.aaslin.developer.finance.client.management.filehandler.presenter.FileBrowsePresenter.FieldUpdateCallback;
import se.aaslin.developer.finance.shared.dto.file.FileDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.datepicker.client.DatePicker;

public class FileBrowseView extends Composite implements FileBrowsePresenter.View {
	
	private final PlaceBrowseViewUIBinder uiBinder = GWT.create(PlaceBrowseViewUIBinder.class);

	public interface PlaceBrowseViewUIBinder extends UiBinder<HTMLPanel, FileBrowseView> {
	}

	@UiField(provided = true) CRUDTable<FileDTO> table;

	public FileBrowseView() {
		table = new CRUDTable<FileDTO>();
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void addFiles(List<FileDTO> files) {
		table.setRowCount(files.size());
		table.setRowData(files);
		table.redraw();
	}

	@Override
	public void setupTable(final FieldUpdateCallback fieldUpdateCallback) {
		Column<FileDTO, Hyperlink> name = new Column<FileDTO, Hyperlink>() {

			@Override
			public Hyperlink getWidget(FileDTO file) {
				return new Hyperlink(file.getName(), file.getToken());
			}	
		};
		table.addColumn(name, "Name");
		
		Column<FileDTO, Label> date = new Column<FileDTO, Label>() {


			@Override
			public Label getWidget(final FileDTO file) {
				final Label label = new Label(DateTimeFormat.getFormat("yyyy QQ MMMM").format(file.getTag()));
				label.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						final PopupPanel popup = new PopupPanel();
						DatePicker datePicker = new DatePicker();
						datePicker.setValue(file.getTag());
						datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
							
							@Override
							public void onValueChange(ValueChangeEvent<Date> event) {
								file.setTag(event.getValue());
								label.setText(DateTimeFormat.getFormat("yyyy QQ MMMM").format(file.getTag()));
								fieldUpdateCallback.onUpdate(file);
								popup.hide();
							}
						});
						popup.setPopupPosition(label.getAbsoluteLeft(), label.getAbsoluteTop() + label.getOffsetHeight());
						popup.add(datePicker);
						
						popup.show();
					}
				});
				
				return label;
			}	
		};
		table.addColumn(date, "Period");
		
		Column<FileDTO, PushButton> remove = new Column<FileDTO, PushButton>() {

			@Override
			public PushButton getWidget(final FileDTO file) {
				PushButton button = new PushButton();
				button.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						fieldUpdateCallback.onRemove(file);
					}
				});
				
				return button;
			}
		};
		table.addColumn(remove);
	}
}
