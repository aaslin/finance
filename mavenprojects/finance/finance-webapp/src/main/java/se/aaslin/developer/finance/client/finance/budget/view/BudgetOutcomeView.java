package se.aaslin.developer.finance.client.finance.budget.view;

import se.aaslin.developer.finance.client.finance.budget.presenter.ShowBudgetMonthPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;

public class BudgetOutcomeView extends Composite implements ShowBudgetMonthPresenter.View {
	public interface BudgetOutcomeViewUIBinder extends UiBinder<HTMLPanel, BudgetOutcomeView> {
	}
	
	BudgetOutcomeViewUIBinder uiBinder = GWT.create(BudgetOutcomeViewUIBinder.class);

	@UiField Panel chartPanel;
	@UiField Panel tablePanel;
	@UiField ListBox listBox;
	
	public BudgetOutcomeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public ListBox getListBox() {
		return listBox;
	}

	@Override
	public Panel getChartPanel() {
		return chartPanel;
	}

	@Override
	public Panel getTablePanel() {
		return tablePanel;
	}
}
