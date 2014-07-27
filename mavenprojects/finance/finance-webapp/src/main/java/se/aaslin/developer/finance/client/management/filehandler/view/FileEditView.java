package se.aaslin.developer.finance.client.management.filehandler.view;

import java.util.List;

import se.aaslin.developer.finance.client.common.widget.CRUDTable;
import se.aaslin.developer.finance.client.common.widget.CRUDTable.Column;
import se.aaslin.developer.finance.client.management.filehandler.presenter.FileEditPresenter;
import se.aaslin.developer.finance.client.management.filehandler.presenter.FileEditPresenter.RemoveCallback;
import se.aaslin.developer.finance.client.management.filehandler.presenter.FileEditPresenter.SelectionCallback;
import se.aaslin.developer.finance.shared.dto.file.FileDataset;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;

public class FileEditView extends Composite implements FileEditPresenter.View {

	private static final DateTimeFormat dateFormat = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT);

	public interface FileHandlerViewUIBinder extends UiBinder<HTMLPanel, FileEditView> {
	}

	FileHandlerViewUIBinder uiBinder = GWT.create(FileHandlerViewUIBinder.class);

	@UiField Label fileLabel;
	@UiField Label errorLabel;
	@UiField Button save;
	@UiField Button reset;
	@UiField(provided = true) CRUDTable<FileDataset> table;

	public FileEditView() {
		table = new CRUDTable<FileDataset>();
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setFilename(String filename) {
		fileLabel.setText("Filnamn: " + filename);
	}

	@Override
	public void setErrorMsg(String msg) {
		errorLabel.setText(msg);
	}

	@Override
	public void setRows(List<FileDataset> rows) {
		table.setRowCount(rows.size());
		table.setRowData(rows);
		table.redraw();
	}

	@Override
	public Button getSaveButton() {
		return save;
	}

	@Override
	public Button getResetButton() {
		return reset;
	}
	
	@Override
	public void setupTable(final List<String> categories, final SelectionCallback selectionCallback, final RemoveCallback removeCallback) {
		Column<FileDataset, Label> date = new Column<FileDataset, Label>() {
			
			@Override
			public Label getWidget(FileDataset ds) {
				return new Label(ds.getDate() == null ? "" : dateFormat.format(ds.getDate()));
			}
		};
		table.addColumn(date, "Datum");

		Column<FileDataset, Label> transaction = new Column<FileDataset, Label>() {

			@Override
			public Label getWidget(FileDataset ds) {
				return new Label(ds.getTransaction());
			}
		};
		table.addColumn(transaction, "Transaktion");

		categories.add(0, CHOOSE_CATEGORY);
		Column<FileDataset, ListBox> category = new Column<FileDataset, ListBox>() {

			@Override
			public ListBox getWidget(final FileDataset ds) {
				final ListBox listBox = new ListBox();
				for (String category : categories) {
					listBox.addItem(category);
				}
				int index = categories.indexOf(ds.getCategory());
				listBox.setSelectedIndex(index == -1 ? 0 : index);
				listBox.addChangeHandler(new ChangeHandler() {
					
					@Override
					public void onChange(ChangeEvent event) {
						String category  =categories.get(listBox.getSelectedIndex());
						selectionCallback.onChange(ds, category);
					}
				});
				
				return listBox;
			}
		};
		table.addColumn(category, "Kategori");

		Column<FileDataset, Label> cost = new Column<FileDataset, Label>() {

			@Override
			public Label getWidget(FileDataset ds) {
				return new Label(ds.getCost().toString());
			}
		};
		table.addColumn(cost, "Belopp");
		
		Column<FileDataset, PushButton> remove = new Column<FileDataset, PushButton>() {

			@Override
			public PushButton getWidget(final FileDataset object) {
				PushButton button = new PushButton();
				button.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						removeCallback.onRemove(object);	
					}
				});
				
				return button;
			}
		};
		table.addColumn(remove);
	}
}
