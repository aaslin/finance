package se.aaslin.developer.finance.client.common.widget;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CRUDTable<T> extends Composite {

	public abstract static class Column<T, W extends Widget> {

		public abstract W getWidget(T object);
	}

	public static class CrudFlexTable extends FlexTable {
		
		public CrudFlexTable() {
			super();
            sinkEvents(Event.ONMOUSEOVER | Event.ONMOUSEOUT);
		}

		@Override
		public void onBrowserEvent(Event event) {
			Element td = getEventTargetCell(event);
			if (td == null || td.getClassName().equals("gwt_crudTable_header")) {
				return;
			}
			Element tr = DOM.getParent(td);
			switch (DOM.eventGetType(event)) {
			case Event.ONMOUSEOVER:
				tr.addClassName("gwt_crudTable_rowHover");
				break;
			case Event.ONMOUSEOUT:
				tr.removeClassName("gwt_crudTable_rowHover");
				break;
			default:
				super.onBrowserEvent(event);
			}
		}

	}

	private FlexTable table;
	private Map<String, Column<T, ? extends Widget>> columns = new LinkedHashMap<String, Column<T, ? extends Widget>>();
	private List<T> rowData = new ArrayList<T>();
	private int rowCount;

	public CRUDTable() {
		table = new CrudFlexTable();
		table.setStylePrimaryName("gwt_crudTable");
		initWidget(table);
	}

	public void setRowCount(int size) {
		rowCount = size;
	}

	public void setRowData(List<T> rowData) {
		this.rowData.clear();
		this.rowData.addAll(rowData);
	}

	public void redraw() {
		table.clear();
		int rowCounter = 0;
		int columnCounter = 0;
		for (Entry<String, Column<T, ? extends Widget>> entry : columns.entrySet()) {
			table.getCellFormatter().setStyleName(rowCounter, columnCounter, "gwt_crudTable_header");
			table.setWidget(rowCounter, columnCounter++, new Label(entry.getKey()));
		}

		for (final T data : rowData) {
			columnCounter = 0;
			rowCounter++;
			if (rowCounter > rowCount) {
				break;
			}
			for (Entry<String, Column<T, ? extends Widget>> entry : columns.entrySet()) {
				final Column<T, ? extends Widget> column = entry.getValue();
				final Widget widget = column.getWidget(data);
				widget.setStylePrimaryName("gwt_crudTable_cell_content");
				table.getCellFormatter().setStyleName(rowCounter, columnCounter, "gwt_crudTable_cell");
				table.setWidget(rowCounter, columnCounter++, widget);
			}
			table.getRowFormatter().setStyleName(rowCounter, (rowCounter % 2 == 0 ? "gwt_crudTable_even" : "gwt_crudTable_odd"));
		}
	}

	public void addColumn(Column<T, ? extends Widget> column, String columnTitle) {
		columns.put(columnTitle, column);
	}

	public void addColumn(Column<T, ? extends Widget> column) {
		addColumn(column, "");
	}
}
