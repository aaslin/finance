package se.aaslin.developer.finance.client.management.category.view;

import java.util.Arrays;
import java.util.List;

import se.aaslin.developer.finance.client.common.widget.CRUDTable;
import se.aaslin.developer.finance.client.common.widget.CRUDTable.Column;
import se.aaslin.developer.finance.client.management.category.presenter.CategoryRulePresenter;
import se.aaslin.developer.finance.client.management.category.presenter.CategoryRulePresenter.RemoveCallback;
import se.aaslin.developer.finance.client.management.category.presenter.CategoryRulePresenter.UpdateCallback;
import se.aaslin.developer.finance.shared.dto.category.CategoryDTO;
import se.aaslin.developer.finance.shared.dto.category.CategoryRuleDTO;
import se.aaslin.developer.finance.shared.dto.category.CategoryRuleOperator;

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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;

public class CategoryRuleView extends Composite implements CategoryRulePresenter.View {

	@UiTemplate("GenericCategoryView.ui.xml")
	public interface GenericCategoryViewUIBinder extends UiBinder<HTMLPanel, CategoryRuleView> {
	}

	private final GenericCategoryViewUIBinder uiBinder = GWT.create(GenericCategoryViewUIBinder.class);

	@UiField(provided = true) CRUDTable<CategoryRuleDTO> table;
	@UiField Button save;
	@UiField Button reset;
	@UiField Button add;

	public CategoryRuleView() {
		table = new CRUDTable<CategoryRuleDTO>();
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void addCategoryRules(List<CategoryRuleDTO> rules) {
		table.setRowCount(rules.size());
		table.setRowData(rules);
		table.redraw();
	}

	@Override
	public void setupTable(final RemoveCallback removeCallback, final UpdateCallback updateCallback, final List<CategoryDTO> categories) {
		Column<CategoryRuleDTO, TextBox> name = new Column<CategoryRuleDTO, TextBox>() {

			@Override
			public TextBox getWidget(final CategoryRuleDTO ruleDTO) {
				TextBox textBox = new TextBox();
				textBox.setText(ruleDTO.getName());
				textBox.addValueChangeHandler(new ValueChangeHandler<String>() {

					@Override
					public void onValueChange(ValueChangeEvent<String> event) {
						ruleDTO.setName(event.getValue());
						ruleDTO.setChanged(true);
						updateCallback.onUpdate();
					}
				});

				return textBox;
			}
		};
		table.addColumn(name, "Name");

		Column<CategoryRuleDTO, ListBox> category = new Column<CategoryRuleDTO, ListBox>() {

			@Override
			public ListBox getWidget(final CategoryRuleDTO ruleDTO) {
				final ListBox listBox = new ListBox();
				for (CategoryDTO c : categories) {
					listBox.addItem(c.getName());
				}
				listBox.setSelectedIndex(categories.indexOf(ruleDTO.getCategory()));
				listBox.addChangeHandler(new ChangeHandler() {

					@Override
					public void onChange(ChangeEvent event) {
						ruleDTO.setCategory(categories.get(listBox.getSelectedIndex()));
						ruleDTO.setChanged(true);
						updateCallback.onUpdate();
					}
				});

				return listBox;
			}
		};
		table.addColumn(category, "Catgeory");

		Column<CategoryRuleDTO, ListBox> operator = new Column<CategoryRuleDTO, ListBox>() {

			@Override
			public ListBox getWidget(final CategoryRuleDTO ruleDTO) {
				final List<CategoryRuleOperator> operators = Arrays.asList(CategoryRuleOperator.values());
				final ListBox listBox = new ListBox();
				for (CategoryRuleOperator c : operators) {
					listBox.addItem(c.name());
				}
				listBox.setSelectedIndex(operators.indexOf(ruleDTO.getOperator()));
				listBox.addChangeHandler(new ChangeHandler() {

					@Override
					public void onChange(ChangeEvent event) {
						ruleDTO.setOperator(operators.get(listBox.getSelectedIndex()));
						ruleDTO.setChanged(true);
						updateCallback.onUpdate();
					}
				});

				return listBox;
			}
		};
		table.addColumn(operator, "Operator");

		Column<CategoryRuleDTO, TextBox> pattern = new Column<CategoryRuleDTO, TextBox>() {

			@Override
			public TextBox getWidget(final CategoryRuleDTO ruleDTO) {
				TextBox textBox = new TextBox();
				textBox.setText(ruleDTO.getPattern());
				textBox.addValueChangeHandler(new ValueChangeHandler<String>() {

					@Override
					public void onValueChange(ValueChangeEvent<String> event) {
						ruleDTO.setPattern(event.getValue());
						ruleDTO.setChanged(true);
						updateCallback.onUpdate();
					}
				});

				return textBox;
			}
		};
		table.addColumn(pattern, "Pattern");

		Column<CategoryRuleDTO, CheckBox> enabled = new Column<CategoryRuleDTO, CheckBox>() {

			@Override
			public CheckBox getWidget(final CategoryRuleDTO ruleDTO) {
				CheckBox checkBox = new CheckBox();
				checkBox.setValue(ruleDTO.isEnabled());
				checkBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

					@Override
					public void onValueChange(ValueChangeEvent<Boolean> event) {
						ruleDTO.setEnabled(event.getValue());
						ruleDTO.setChanged(true);
						updateCallback.onUpdate();
					}
				});

				return checkBox;
			}
		};
		table.addColumn(enabled, "Enable");

		Column<CategoryRuleDTO, PushButton> remove = new Column<CategoryRuleDTO, PushButton>() {

			@Override
			public PushButton getWidget(final CategoryRuleDTO ruleDTO) {
				PushButton button = new PushButton();
				button.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						removeCallback.onRemove(ruleDTO);
					}
				});

				return button;
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
