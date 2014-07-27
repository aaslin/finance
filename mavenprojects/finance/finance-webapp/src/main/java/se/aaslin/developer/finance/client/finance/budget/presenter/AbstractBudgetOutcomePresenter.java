package se.aaslin.developer.finance.client.finance.budget.presenter;

import java.util.List;

import se.aaslin.developer.finance.client.AbstractCallback;
import se.aaslin.developer.finance.client.ClientFactory;
import se.aaslin.developer.finance.client.finance.budget.service.AbstractBudgetOutcomeService;
import se.aaslin.developer.finance.shared.dto.BudgetOutcomeDTO;
import se.aaslin.developer.finance.shared.dto.TimeInterval;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;

public abstract class AbstractBudgetOutcomePresenter extends AbstractActivity {

	public interface View extends IsWidget {

		Panel getChartPanel();

		ListBox getListBox();

		Panel getTablePanel();
	}

	private View view;
	private AbstractBudgetOutcomeService srv;
	private ClientFactory clientFactory;
	private List<TimeInterval> intervals;
	private int selectedInterval;

	public AbstractBudgetOutcomePresenter(View view, AbstractBudgetOutcomeService srv, ClientFactory clientFactory) {
		this.view = view;
		this.srv = srv;
		this.clientFactory = clientFactory;

		fetchTimeIntervals();
		bind();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view);
	}

	private void fetchTimeIntervals() {
		srv.getTimeIntervals(new AbstractCallback<List<TimeInterval>>() {

			@Override
			public void onSuccess(List<TimeInterval> response) {
				addTimeIntervals(response);
				fetchOutcome();
			}
		});
	}

	private void addTimeIntervals(List<TimeInterval> intervals) {
		this.intervals = intervals;
		view.getListBox().clear();
		for (TimeInterval interval : intervals) {
			view.getListBox().addItem(interval.getName());
		}
		view.getListBox().setSelectedIndex(0);
	}

	private void bind() {
		view.getListBox().addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				selectedInterval = view.getListBox().getSelectedIndex();
				fetchOutcome();
			}
		});
	}

	private void fetchOutcome() {
		TimeInterval interval = intervals.get(selectedInterval);
		srv.getOutome(interval, new AbstractCallback<List<BudgetOutcomeDTO>>() {

			@Override
			public void onSuccess(List<BudgetOutcomeDTO> response) {
				final Runnable chartSetup = createChartSetup(response);
				// final Runnable tableSetup = createTableSetup(response);
				VisualizationUtils.loadVisualizationApi(new Runnable() {

					@Override
					public void run() {
						chartSetup.run();
						// tableSetup.run();
					}
				}, ColumnChart.PACKAGE, Table.PACKAGE);
			}
		});
	}

	private Runnable createChartSetup(final List<BudgetOutcomeDTO> dtos) {
		return new Runnable() {
			public void run() {
				ColumnChart chart = new ColumnChart(createBarTable(dtos), createBarOptions());
				view.getChartPanel().clear();
				view.getChartPanel().add(chart);
			}

			private Options createBarOptions() {
				Options options = Options.create();
				options.setWidth(750);
				options.setHeight(500);
				options.setTitle("Outcome");
				options.set("animation", getOptionAnimation());
				options.set("series", getOptionSeries());
				options.setVAxisOptions(createVAxisOptions());
				options.setColors("#43aad5", "#e59949");

				return options;
			}

			private AbstractDataTable createBarTable(List<BudgetOutcomeDTO> dtos) {
				DataTable data = DataTable.create();
				data.addColumn(ColumnType.STRING, "Category");
				data.addColumn(ColumnType.NUMBER, "Outcome");
				data.addColumn(ColumnType.NUMBER, "Budget");
				data.addRows(dtos.size());
				int rowCounter = 0;
				for (BudgetOutcomeDTO dto : dtos) {
					data.setValue(rowCounter, 0, dto.getName());
					data.setValue(rowCounter, 1, -dto.getCost().doubleValue());
					data.setValue(rowCounter++, 2, -dto.getBudget().doubleValue());
				}

				return data;
			}
			
			private AxisOptions createVAxisOptions() {
				AxisOptions options = AxisOptions.create();
                options.setMinValue(0.0);
                options.setMaxValue(10000);

                return options;

			}


			protected native JavaScriptObject getOptionAnimation() /*-{
																	var anim = {};
																	anim.duration = 800;
																	return anim;
																	}-*/;

			protected native JavaScriptObject getOptionSeries() /*-{
																var series = {};
																series = {
																0 : {
																pointSize : '5',
																lineWidth : '0'

																},
																1 : {
																curveType : 'function'

																},
																3 : {
																lineWidth : '3'
																}

																};
																return series;
																}-*/;
		};
	}
}
