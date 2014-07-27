package se.aaslin.developer.finance.client.management.category.view;

import java.util.List;

import se.aaslin.developer.finance.client.common.widget.CRUDTable;
import se.aaslin.developer.finance.client.common.widget.CRUDTable.Column;
import se.aaslin.developer.finance.client.management.category.presenter.CategoryBrowsePresenter;
import se.aaslin.developer.finance.client.management.category.presenter.CategoryBrowsePresenter.RemoveCallback;
import se.aaslin.developer.finance.client.management.category.presenter.CategoryBrowsePresenter.UpdateCallback;
import se.aaslin.developer.finance.shared.dto.category.CategoryDTO;
import se.aaslin.developer.finance.shared.dto.category.CategoryType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;

public class CategoryBrowseView extends Composite implements CategoryBrowsePresenter.View {

	@UiTemplate("GenericCategoryView.ui.xml")
	public interface GenericCategoryViewUIBinder extends UiBinder<HTMLPanel, CategoryBrowseView> {
	}
	
	private final GenericCategoryViewUIBinder uiBinder = GWT.create(GenericCategoryViewUIBinder.class);

	@UiField(provided = true) CRUDTable<CategoryDTO> table;
	@UiField Button save;
	@UiField Button reset;
	@UiField Button add;

	public CategoryBrowseView() {
		table = new CRUDTable<CategoryDTO>();
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void addCategories(List<CategoryDTO> categories) {
		table.setRowCount(categories.size());
		table.setRowData(categories);
		table.redraw();
	}

	@Override
	public void setupTable(final RemoveCallback removeCallback, final UpdateCallback updateCallback) {
		Column<CategoryDTO, TextBox> name = new Column<CategoryDTO, TextBox>() {

			@Override
			public TextBox getWidget(final CategoryDTO dto) {
				TextBox textBox = new TextBox();
				textBox.setText(dto.getName());
				textBox.addValueChangeHandler(new ValueChangeHandler<String>() {

					@Override
					public void onValueChange(ValueChangeEvent<String> event) {
						dto.setName(event.getValue());
						dto.setChanged(true);
						updateCallback.onUpdate();
					}
				});

				return textBox;
			}
		};
		table.addColumn(name, "Name");

		Column<CategoryDTO, ListBox> type = new Column<CategoryDTO, ListBox>() {

			@Override
			public ListBox getWidget(final CategoryDTO dto) {
				final ListBox listBox = new ListBox();
				listBox.addItem(CategoryType.EXPENSE.getName());
				listBox.addItem(CategoryType.INCOME.getName());
				listBox.setSelectedIndex(dto.getType() == CategoryType.EXPENSE ? 0 : 1);
				listBox.addChangeHandler(new ChangeHandler() {

					@Override
					public void onChange(ChangeEvent event) {
						dto.setType(CategoryType.parse(listBox.getItemText(listBox.getSelectedIndex())));
						dto.setChanged(true);
						updateCallback.onUpdate();
					}
				});

				return listBox;
			}
		};
		table.addColumn(type, "Type");

		Column<CategoryDTO, PushButton> remove = new Column<CategoryDTO, PushButton>() {

			@Override
			public PushButton getWidget(final CategoryDTO dto) {
				PushButton remove = new PushButton();
				remove.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						removeCallback.onRemove(dto);
					}
				});

				return remove;
			}
		};
		table.addColumn(remove);
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
	public Button getAddButton() {
		return add;
	}
}
