package com.hiepnx.cms.client.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gwtbootstrap3.client.ui.ListBox;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.hiepnx.cms.shared.Config;
import com.hiepnx.cms.shared.model.IBasic;

public class HListBox extends ListBox {
	private HorizontalPanel hPanel = null;
	private HTML label = null;
	private int width = 0;
	private Map<String, IBasic> itemMap = new HashMap<String, IBasic>();
	
	public HListBox() {
		super();
		this.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
	}
	
	public HListBox(String title, int width) {
		this();
		setValues(title, width);
	}
	
	public void setValues(String title, int width) {
		this.width = width;
		if (title != null && !title.isEmpty()) {
			label = new HTML(title);
			label.getElement().getStyle().setProperty("padding", "0px 5px");
			hPanel = new HorizontalPanel();
			hPanel.setSpacing(5);
			hPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			hPanel.add(label);
			hPanel.setCellHorizontalAlignment(label, HasHorizontalAlignment.ALIGN_RIGHT);
			hPanel.add(this);
			hPanel.setCellHorizontalAlignment(this, HasHorizontalAlignment.ALIGN_LEFT);
		}
		if (width > 0) {
			if (hPanel != null) {
				hPanel.setWidth(width + "px");
			}
			else {
				this.setWidth(width + "px");
			}
		}
	}
	
	public void addItem(String text, String value, IBasic item) {
		super.addItem(text, value);
		if (item != null) {
			itemMap.put(value, item);
		}
	}
	
	public IBasic getSelectedItem() {
		return itemMap.get(this.getSelectedValue());
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public Widget asWidget() {
		return hPanel != null ? hPanel : this;
	}
	
	public void setSelectedValue(int value) {
		setSelectedValue(value + "");
	}

	public void setSelectedValue(long value) {
		setSelectedValue(value + "");
	}
	
	public long getLongValue() {
		long selectedValue = Config.NULL_ID;
		try {
			selectedValue = Long.parseLong(this.getSelectedValue());
		}
		catch (Exception e) {}
		return selectedValue;
	}

	public int getIntValue() {
		int selectedValue = -1;
		try {
			selectedValue = Integer.parseInt(this.getSelectedValue());
		}
		catch (Exception e) {}
		return selectedValue;
	}
	
	public void setSelectedValue(String value) {
		for (int i = 0; i < this.getItemCount(); i ++) {
			if (this.getValue(i).equalsIgnoreCase(value)) {
				this.setSelectedIndex(i);
				break;
			}
		}
	}
	
	public List<Long> getItemIds() {
		List<Long> itemIds = new ArrayList<Long>();
		for (int i = 0; i < this.getItemCount(); i ++) {
			long id = -1;
			try {
				id = Long.parseLong(this.getValue(i));
			}
			catch (Exception e) {
			}
			if (id > 0 && !itemIds.contains(id)) {
				itemIds.add(id);
			}
		}
		return itemIds;
	}
}
