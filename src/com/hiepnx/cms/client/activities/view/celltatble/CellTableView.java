package com.hiepnx.cms.client.activities.view.celltatble;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.CheckBox;
import org.gwtbootstrap3.client.ui.gwt.CellTable;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;
import com.hiepnx.cms.client.view.HListBox;
import com.hiepnx.cms.shared.Config;
import com.hiepnx.cms.shared.model.IBasic;

public class CellTableView extends VerticalPanel {
	
	protected ListHandler<IBasic> sortHandler;
	protected HorizontalPanel topTablePanel;
	protected HorizontalPanel bottomTablePanel;
	protected CheckBox topCheckBox;
	protected CheckBox bottomCheckBox;
	protected SimplePager topPager, bottomPager;
	protected CellTable<IBasic> cellTable;
	protected HListBox pageSizeListBox = new HListBox("Số dòng", 150);
	protected PopupPanel tooltip = new PopupPanel(true);
	protected Button sendEmail = null;
	protected int currentPage = 0;
	protected HTML tableTitle = new HTML("");

	protected ListDataProvider<IBasic> dataProvider = new ListDataProvider<IBasic>();
	protected SelectionModel<IBasic> selectionModel;

	public static final ProvidesKey<IBasic> KEY_PROVIDER = new ProvidesKey<IBasic>() {
		@Override
		public Object getKey(IBasic item) {
			return item == null ? null : item.getId() == null ? Config.NULL_ID : item.getId();
		}
	};

	public CellTableView() {
		this(true, true);
	}
	
	public CellTableView(boolean hasPaging, boolean hasSelectAll) {
		super();
		topTablePanel = new HorizontalPanel();
		bottomTablePanel  = new HorizontalPanel();
		topCheckBox = new CheckBox("Select All");
		bottomCheckBox = new CheckBox("Select All");
		cellTable = initCellTable(dataProvider);
		this.addStyleName("cellTablePanel");
		this.add(tableTitle);
		this.add(topTablePanel);
		this.add(cellTable);
		this.add(bottomTablePanel);
		// Create a Pager to control the table.
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		topPager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		topPager.setDisplay(cellTable);

		bottomPager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		bottomPager.setDisplay(cellTable);
		if (hasSelectAll) {
			topTablePanel.add(topCheckBox);
			topTablePanel.setCellWidth(topCheckBox, "100px");
			topTablePanel.setCellHorizontalAlignment(topCheckBox, HasHorizontalAlignment.ALIGN_LEFT);
			topTablePanel.setCellVerticalAlignment(topCheckBox, ALIGN_MIDDLE);
			topCheckBox.getElement().getStyle().setPadding(10, Unit.PX);
			topCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					List<IBasic> list = dataProvider.getList();
					bottomCheckBox.setValue(event.getValue());
					if (list != null) {
						for (IBasic item : list) {
							selectionModel.setSelected(item, event.getValue());
						}
						cellTable.redraw();
					}
				}
			});
		}
		if (hasPaging) {
			topTablePanel.add(topPager);
			topTablePanel.add(pageSizeListBox.asWidget());
			topTablePanel.setCellWidth(pageSizeListBox.asWidget(), "150px");
			topTablePanel.setCellHorizontalAlignment(pageSizeListBox.asWidget(), HasHorizontalAlignment.ALIGN_RIGHT);
			topTablePanel.setCellVerticalAlignment(pageSizeListBox.asWidget(), ALIGN_MIDDLE);
		}
		pageSizeListBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent arg0) {
				setPageSize(pageSizeListBox.getIntValue());
			}
		});
		pageSizeListBox.addItem("30", "30");
		pageSizeListBox.addItem("50", "50");
		pageSizeListBox.addItem("100", "100");
		pageSizeListBox.addItem("200", "300");
		if (hasSelectAll) {
			bottomTablePanel.add(bottomCheckBox);
			bottomTablePanel.setCellWidth(bottomCheckBox, "100px");
			bottomTablePanel.setCellHorizontalAlignment(bottomCheckBox, HasHorizontalAlignment.ALIGN_LEFT);
			bottomCheckBox.getElement().getStyle().setPadding(10, Unit.PX);
			bottomCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					List<IBasic> list = dataProvider.getList();
					topCheckBox.setValue(event.getValue());
					if (list != null) {
						for (IBasic item : list) {
							selectionModel.setSelected(item, event.getValue());
						}
						cellTable.redraw();
					}
				}
			});
		}
		if (hasPaging) {
			bottomTablePanel.add(bottomPager);
		}
		topTablePanel.setWidth("100%");
		topPager.setWidth("250px");
		topTablePanel.setCellHorizontalAlignment(topPager, HasHorizontalAlignment.ALIGN_CENTER);
		topTablePanel.setCellVerticalAlignment(topPager, ALIGN_MIDDLE);
		
		bottomTablePanel.setWidth("100%");
		bottomPager.setWidth("250px");
		bottomTablePanel.setCellHorizontalAlignment(bottomPager, HasHorizontalAlignment.ALIGN_CENTER);
		tooltip.getElement().getStyle().setBackgroundColor("yellow");
	}
	
	public void setTableTitle(String title) {
		tableTitle.setHTML(title);
	}
	
	public PopupPanel getToolTip() {
		return this.tooltip;
	}
	
	public void setup(boolean hasPaging, boolean hasSelectAll) {
		if (!hasSelectAll) {
			topCheckBox.removeFromParent();
			bottomCheckBox.removeFromParent();
		}
	}
	
	public void setConfig(boolean hasPaging, boolean hasSelectAll) {
		pageSizeListBox.setVisible(hasPaging);
		topCheckBox.setVisible(hasSelectAll);
		bottomCheckBox.setVisible(hasSelectAll);
	}
	
	public List<IBasic> getSelectedItems() {
		List<IBasic> selected = new ArrayList<IBasic>();
		List<IBasic> list = dataProvider.getList();
		if (list != null) {
			for (IBasic item : list) {
				if (selectionModel.isSelected(item)) {
					selected.add(item);
				}
			}
		}
		return selected;
	}
	
	public boolean hasSelectedItem() {
		List<IBasic> list = dataProvider.getList();
		if (list != null) {
			for (IBasic item : list) {
				if (selectionModel.isSelected(item)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void setPageSize(int size) {
		topPager.setPageSize(size);
		bottomPager.setPageSize(size);
	}
	
	public static interface GetValue<C> {
		C getValue(IBasic contact);
	}
	
	public static interface GetCellStyle<C> {
		C getCellStyleNames(Context context, IBasic object);
	}
	
	public HListBox getPageSizeListBox() {
		return this.pageSizeListBox;
	}
	
	public void setPagerSize(List<Integer> sizes, int defaultSize) {
		pageSizeListBox.clear();
		int selectedIndex = 0;
		for (Integer size : sizes) {
			pageSizeListBox.addItem(size + "", size + "");
			if (defaultSize > 0 && defaultSize == size) {
				selectedIndex = pageSizeListBox.getItemCount() - 1;
			}
		}
		pageSizeListBox.setSelectedIndex(selectedIndex);
	}
	
	public void refresh() {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				getDataProvider().refresh();
				getCellTable().redraw();
			}
		});
	}
	
	public Button getSendMailButton() {
		return this.sendEmail;
	}
	
	public void setData(List<? extends IBasic> items) {
		setData(items, true, -1);
	}
	
	public void setData(List<? extends IBasic> items, int totalNum) {
		setData(items, true, totalNum);
	}
	
	public void setData(List<? extends IBasic> items, boolean isShowToaster) {
		setData(items, isShowToaster, -1);
	}
	
	public void setData(List<? extends IBasic> items, boolean isShowToaster, int totalNum) {
		if (items == null) {
			items = new ArrayList<IBasic>();
		}
		if (items.isEmpty()) {
			while (cellTable.getColumnCount() > 0) {
				cellTable.removeColumn(0);
			}
			cellTable.setEmptyTableWidget(new HTML("Không có dữ liệu ..."));
			dataProvider.setList(new ArrayList<IBasic>(items));
			dataProvider.refresh();
		}
		else {
			while (cellTable.getColumnCount() > 0) {
				cellTable.removeColumn(0);
			}
			setPageSize(pageSizeListBox.getIntValue());
			dataProvider.setList(new ArrayList<IBasic>(items));
			sortHandler.setList(dataProvider.getList());
		}
	}
	
	public void updateData(List<? extends IBasic> items) {
		setPageSize(pageSizeListBox.getIntValue());
		dataProvider.setList(new ArrayList<IBasic>(items));
		sortHandler.setList(dataProvider.getList());
	}
	
	public Column<IBasic, Boolean> addCheckBoxColumn() {
		return addCheckBoxColumn(20);
	}
	
	public Column<IBasic, Boolean> addCheckBoxColumn(int width) {
	    Column<IBasic, Boolean> checkColumn = new Column<IBasic, Boolean>(new CheckboxCell(true, false)) {
		      @Override
		      public Boolean getValue(IBasic object) {
		        return selectionModel.isSelected(object);
		      }
		    };
		    cellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		    cellTable.setColumnWidth(checkColumn, width, Unit.PX);
		    return checkColumn;
	}
	
	public CellTable<IBasic> initCellTable(
			ListDataProvider<IBasic> dataProvider) {
		CellTable<IBasic> cellTable = new CellTable<IBasic>(KEY_PROVIDER);
		cellTable.setWidth("100%", true);
		cellTable.setEmptyTableWidget(new HTML("Đang tải dữ liệu ..."));
		// Do not refresh the headers and footers every time the data is
		// updated.
		cellTable.setAutoHeaderRefreshDisabled(true);
		cellTable.setAutoFooterRefreshDisabled(true);

		// Attach a column sort handler to the ListDataProvider to sort the
		// list.
		sortHandler = new ListHandler<IBasic>(dataProvider.getList());
		cellTable.addColumnSortHandler(sortHandler);

		// Add a selection model so we can select cells.
		selectionModel = new MultiSelectionModel<IBasic>(KEY_PROVIDER);
		cellTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<IBasic> createCheckboxManager());
		// Initialize the columns.
		// Add the CellList to the adapter in the database.
		dataProvider.addDataDisplay(cellTable);
		return cellTable;
	}
	
	public ListDataProvider<IBasic> getDataProvider() {
		return this.dataProvider;
	}
	
	public SelectionModel<IBasic> getSelectionModel() {
		return this.selectionModel;
	}
	
	public ListHandler<IBasic> getSortHandler() {
		return this.sortHandler;
	}
	
	public CellTable<IBasic> getCellTable() {
		return this.cellTable;
	}
	
	public SimplePager getTopPager() {
		return topPager;
	}
	
	public HorizontalPanel getTopTablePanel() {
		return topTablePanel;
	}

	public HorizontalPanel getBottomTablePanel() {
		return bottomTablePanel;
	}

	/**
	 * Get a cell value from a record.
	 * 
	 * @param <C>
	 *            the cell type
	 */
//	protected static interface GetValue<C> {
//		C getValue(IBasic contact);
//	}
//	
//	protected static interface GetCellStyle<String> {
//		String getCellStyleNames(Context context, IBasic object);
//	}
	
	public <C> Column<IBasic, C> addColumn(CellTable<IBasic> cellTable,
			int width, Cell<C> cell, String headerText,
			final GetValue<C> getter, GetCellStyle<String> cellStyle, FieldUpdater<IBasic, C> fieldUpdater) {
		return this.addColumn(cellTable, width, null, cell, headerText, null, getter, cellStyle,
				fieldUpdater, null);
	}

	public <C> Column<IBasic, C> addColumn(CellTable<IBasic> cellTable,
			int width, Cell<C> cell, String headerText, String headerStyle,
			final GetValue<C> getter, GetCellStyle<String> cellStyle, FieldUpdater<IBasic, C> fieldUpdater) {
		return this.addColumn(cellTable, width, null, cell, headerText, headerStyle, getter, cellStyle,
				fieldUpdater, null);
	}

	public <C> Column<IBasic, C> addColumn(CellTable<IBasic> cellTable,
			int width, Cell<C> cell, String headerText,
			final GetValue<C> getter, GetCellStyle<String> cellStyle, FieldUpdater<IBasic, C> fieldUpdater,
			Comparator<IBasic> comparator) {
		return this.addColumn(cellTable, width, null, cell, headerText, null, getter, cellStyle,
				fieldUpdater, comparator);
	}
	
	public <C> Column<IBasic, C> addColumn(CellTable<IBasic> cellTable,
			int width, Cell<C> cell, String headerText, String headerStyle,
			final GetValue<C> getter, GetCellStyle<String> cellStyle, FieldUpdater<IBasic, C> fieldUpdater,
			Comparator<IBasic> comparator) {
		return this.addColumn(cellTable, width, null, cell, headerText, headerStyle, getter, cellStyle,
				fieldUpdater, comparator);
	}
	
	public <C> Column<IBasic, C> addColumn(CellTable<IBasic> cellTable,
			int width, Cell<C> cell, String headerText,
			final GetValue<C> getter, FieldUpdater<IBasic, C> fieldUpdater) {
		return this.addColumn(cellTable, width, null, cell, headerText, null, getter, null,
				fieldUpdater, null);
	}
	
	public <C> Column<IBasic, C> addColumn(CellTable<IBasic> cellTable,
			int width, Cell<C> cell, String headerText, String headerStyle,
			final GetValue<C> getter, FieldUpdater<IBasic, C> fieldUpdater) {
		return this.addColumn(cellTable, width, null, cell, headerText, headerStyle, getter, null,
				fieldUpdater, null);
	}
	
	public <C> Column<IBasic, C> addColumn(CellTable<IBasic> cellTable,
			int width, Cell<C> cell, String headerText,
			final GetValue<C> getter, FieldUpdater<IBasic, C> fieldUpdater,
			Comparator<IBasic> comparator) {
		return this.addColumn(cellTable, width, null, cell, headerText, null, getter, null,
				fieldUpdater, comparator);
	}

	public <C> Column<IBasic, C> addColumn(CellTable<IBasic> cellTable,
			int width, Cell<C> cell, String headerText, String headerStyle,
			final GetValue<C> getter, FieldUpdater<IBasic, C> fieldUpdater,
			Comparator<IBasic> comparator) {
		return this.addColumn(cellTable, width, null, cell, headerText, headerStyle, getter, null,
				fieldUpdater, comparator);
	}

	/**
	 * Add a column with a header.
	 * 
	 * @param <C>
	 *            the cell type
	 * @param cell
	 *            the cell used to render the column
	 * @param headerText
	 *            the header string
	 * @param getter
	 *            the value getter for the cell
	 */
	protected <C> Column<IBasic, C> addColumn(CellTable<IBasic> cellTable,
			int width, HorizontalAlignmentConstant align, Cell<C> cell,
			String headerText, String headerStyle, final GetValue<C> getter,
			final GetCellStyle<String> cellStyle,
			FieldUpdater<IBasic, C> fieldUpdater, Comparator<IBasic> comparator) {
		Column<IBasic, C> column = new Column<IBasic, C>(cell) {
			@Override
			public C getValue(IBasic object) {
				return getter.getValue(object);
			}
			@Override
		    public String getCellStyleNames(Context context, IBasic object) {
				if (cellStyle != null) {
					String style = cellStyle.getCellStyleNames(context, object);
					if (style != null && !style.isEmpty()) {
						return style;
					}
				}
				return super.getCellStyleNames(context, object);
		    }
		};
		if (comparator != null) {
			column.setSortable(true);
			sortHandler.setComparator(column, comparator);
		}
		column.setFieldUpdater(fieldUpdater);
//		if (cell instanceof AbstractEditableCell<?, ?>) {
//			editableCells.add((AbstractEditableCell<?, ?>) cell);
//		}
		TextHeader header = new TextHeader(headerText);
		if (headerStyle != null && !headerStyle.isEmpty()) {
			header.setHeaderStyleNames(headerStyle);
		}
		cellTable.addColumn(column, header);
		if (width > 0)
			cellTable.setColumnWidth(column, width, Unit.PX);
		if (align != null)
			column.setHorizontalAlignment(align);
		return column;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}