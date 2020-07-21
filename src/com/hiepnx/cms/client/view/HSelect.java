package com.hiepnx.cms.client.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gwtbootstrap3.extras.select.client.ui.OptGroup;
import org.gwtbootstrap3.extras.select.client.ui.Option;
import org.gwtbootstrap3.extras.select.client.ui.Select;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Command;
import com.hiepnx.cms.shared.AccentRemover;
import com.hiepnx.cms.shared.Config;
import com.hiepnx.cms.shared.model.IBasic;

public class HSelect extends Select {
	
	private Map<String, Option> optionMap = new HashMap<String, Option>();
	private Map<String, OptGroup> groupMap = new HashMap<String, OptGroup>();
	private Map<String, IBasic> itemMap = new HashMap<String, IBasic>();
	
	public HSelect() {
		super();
		//this.getElement().getStyle().setZIndex(Config.ZINDEX_LEVEL3);
		this.setDropupAuto(false);
		this.setForceDropup(false);
	}
	
	public HSelect(String title, int width) {
		this();
	}
		
	public void setSelectedValue(String value) {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				HSelect.super.setSelectedValue(value);
			}
		});
	}
	
	public String getSelectedValue() {
		return this.getSelectedItem() != null ? this.getSelectedItem().getValue() : Config.TEXT_EMPTY;
	}
	
	public String getSelectedItemText() {
		return this.getSelectedItem() != null ? this.getSelectedItem().getText() : Config.TEXT_EMPTY;
	}
	
	public Long getLongValue(int index) {
		Option option = this.getItem(index);
		if (option != null) {
			try {
				return Long.parseLong(option.getValue());
			}
			catch (Exception e) {
			}
		}
		return Config.NULL_ID;
	}
	
	public IBasic getItem() {
		return this.getSelectedItem() != null ? itemMap.get(this.getSelectedItem().getId()) : null;
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
			selectedValue = Long.parseLong(this.getSelectedItem().getId());
		}
		catch (Exception e) {}
		return selectedValue;
	}

	public int getIntValue() {
		int selectedValue = -1;
		try {
			selectedValue = Integer.parseInt(this.getSelectedItem().getId());
		}
		catch (Exception e) {}
		return selectedValue;
	}
	
	public void setSelectedId(String id) {
		Scheduler.get().scheduleDeferred( new Command() {
	        public void execute() {
	        	for (Option option : getItems()) {
					if (option.getId().equalsIgnoreCase(id)) {
						option.setSelected(true);
						refreshSelect();
						break;
					}
				}
	        }
	    } );
	}
	
	public void refreshSelect() {
	    Scheduler.get().scheduleDeferred( new Command() {
	        public void execute() {
	            refresh();
	        }
	    } );
	}

	public void addItem(String text, String value, IBasic item) {
		addItem(text, value);
		if (item != null) {
			itemMap.put(value, item);
		}
	}
	
	public Option addItem(String text, String value) {
		Option option = new Option();
		option.setText(text);
		option.setValue(value);
		option.setTokens(AccentRemover.removeAccent(text.toLowerCase()));
		option.setId(value);
		this.add(option);
		optionMap.put(option.getId(), option);
		return option;
	}
	
	public Option addItem(String groupName, String text, String value, boolean selected) {
		return this.addItem(groupName, text, value, selected, null);
	}
	
	public Option addItem(String groupName, String text, String value, boolean selected, IBasic item) {
		Option option = new Option();
		option.setText(text);
		option.setValue(value);
		option.setTokens(AccentRemover.removeAccent(text.toLowerCase()));
		option.setId(value);
		option.setSelected(selected);
		if (item != null) {
			itemMap.put(value, item);
		}
		optionMap.put(option.getId(), option);
		if (groupName != null && !groupName.isEmpty()) {
			OptGroup group = groupMap.get(groupName);
			if (group == null) {
				group = new OptGroup();
				this.add(group);
				group.setLabel(groupName);
			}
			group.add(option);
			groupMap.put(groupName, group);
		}
		else {
			this.add(option);
		}
		return option;
	}
	
	public void setText(String id, String text) {
		Option option = this.optionMap.get(id);
		if (option != null) {
			option.setText(text);
		}
	}
	
	public void reset() {
		for (Option option : this.getItems()) {
			option.setSelected(false);
		}
		this.refreshSelect();
	}
	
	public void addOption(String id, String value, String text, IBasic item) {
		addOption(id, value, text);
		itemMap.put(id, item);
	}
	
	public void addOption(String id, String value, String text) {
		Option option = new Option();
		option.setText(text);
		option.setValue(value);
		option.setId(id);
		optionMap.put(option.getId(), option);
		this.add(option);
	}
	
	public List<Long> getItemIds() {
		List<Long> itemIds = new ArrayList<Long>();
		for (int i = 0; i < this.getItemCount(); i ++) {
			long id = -1;
			try {
				id = Long.parseLong(this.getItem(i).getValue());
			}
			catch (Exception e) {
			}
			if (id > 0 && !itemIds.contains(id)) {
				itemIds.add(id);
			}
		}
		return itemIds;
	}
	
	@Override
	public void clear() {
		super.clear();
		optionMap.clear();
		groupMap.clear();
		itemMap.clear();
	}
	
	public boolean isSelectedAll() {
		Option option = this.getSelectedItem();
		return option != null && option.getValue().equalsIgnoreCase(Config.NULL_ID + "");
	}
}