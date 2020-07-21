package com.hiepnx.cms.client.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gwtbootstrap3.extras.select.client.ui.MultipleSelect;
import org.gwtbootstrap3.extras.select.client.ui.OptGroup;
import org.gwtbootstrap3.extras.select.client.ui.Option;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Command;
import com.hiepnx.cms.shared.model.IBasic;

public class HMultipleSelect extends MultipleSelect {

	private Map<String, Option> optionMap = new HashMap<String, Option>();
	private Map<String, OptGroup> groupMap = new HashMap<String, OptGroup>();
	private Map<Object, IBasic> itemMap = new HashMap<Object, IBasic>();

	public HMultipleSelect() {
		super();
	}

	public HMultipleSelect(String title, int width) {
		this();
	}

	public void addItem(String groupName, String text, String value, boolean selected, String color, IBasic item) {
		Option option = addItem(groupName, text, value, selected, item);
		if (option != null && color != null && color.length() > 1) {
			option.setColor(color);
		}
	}

	public Option addItem(String groupName, String text, String value, boolean selected, IBasic item) {
		Option option = new Option();
		option.setText(text);
		option.setValue(value);
		option.setId(value);
		option.setSelected(selected);
		if (groupName != null && !groupName.isEmpty()) {
			OptGroup group = groupMap.get(groupName);
			if (group == null) {
				group = new OptGroup();
				this.add(group);
				group.setLabel(groupName);
			}
			group.add(option);
			groupMap.put(groupName, group);
		} else {
			this.add(option);
		}
		if (item != null) {
			itemMap.put(value, item);
		}
		optionMap.put(option.getId(), option);
		return option;
	}

	public void setSelectedIds(List<? extends Object> ids) {
		if (ids == null || ids.isEmpty()) {
			reset();
			return;
		}
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				if (ids.get(0) instanceof Long) {
					for (Option option : getItems()) {
						if (ids.contains(Long.parseLong(option.getId()))) {
							option.setSelected(true);
						}
					}
				} else if (ids.get(0) instanceof Integer) {
					for (Option option : getItems()) {
						if (ids.contains(Integer.parseInt(option.getId()))) {
							option.setSelected(true);
						}
					}
				} else if (ids.get(0) instanceof String) {
					for (Option option : getItems()) {
						if (ids.contains(option.getId())) {
							option.setSelected(true);
						}
					}
				}
				refreshSelect();
			}
		});
	}

	public List<Long> getSelectedLongIds() {
		List<Long> ids = new ArrayList<Long>();
		List<Option> options = this.getSelectedItems();
		for (Option option : options) {
			try {
				ids.add(Long.parseLong(option.getId()));
			} catch (Exception e) {
			}
		}
		return ids;
	}

	public List<Long> getLongIds() {
		List<Long> ids = new ArrayList<Long>();
		List<Option> options = this.getItems();
		for (Option option : options) {
			try {
				ids.add(Long.parseLong(option.getId()));
			} catch (Exception e) {
			}
		}
		return ids;
	}

	public List<String> getSelectedTexts() {
		List<String> names = new ArrayList<String>();
		List<Option> options = this.getSelectedItems();
		for (Option option : options) {
			names.add(option.getText());
		}
		return names;
	}

	public List<IBasic> getSelectedIBasics() {
		List<IBasic> items = new ArrayList<IBasic>();
		List<Option> options = this.getSelectedItems();
		for (Option option : options) {
			IBasic item = itemMap.get(option.getValue());
			if (item != null) {
				items.add(item);
			}
		}
		return items;
	}

	public List<Integer> getSelectedIntIds() {
		List<Integer> ids = new ArrayList<Integer>();
		List<Option> options = this.getSelectedItems();
		for (Option option : options) {
			try {
				ids.add(Integer.parseInt(option.getId()));
			} catch (Exception e) {
			}
		}
		return ids;
	}

	public void setSelected(String id, boolean selected) {
		Option option = optionMap.get(id);
		if (option != null) {
			option.setSelected(selected);
		}
		this.refreshSelect();
	}

	public List<String> getSelectedIds() {
		List<String> ids = new ArrayList<String>();
		List<Option> options = this.getSelectedItems();
		for (Option option : options) {
			try {
				ids.add(option.getId());
			} catch (Exception e) {
			}
		}
		return ids;
	}

	public void refreshSelect() {
		Scheduler.get().scheduleDeferred(new Command() {
			public void execute() {
				refresh();
			}
		});
	}

	public void setText(String id, String text) {
		if (this.optionMap.get(id) != null) {
			this.optionMap.get(id).setText(text);
		}
	}

	public void addOption(String text, String value) {
		this.addOption(value, text, value);
	}

	public void addOption(String id, String text, String value) {
		Option option = new Option();
		option.setText(text);
		option.setValue(value);
		option.setId(id);
		optionMap.put(option.getId(), option);
		this.add(option);
	}

	public void reset() {
		for (Option option : this.getItems()) {
			option.setSelected(false);
		}
		this.refreshSelect();
	}

	public boolean isSelectedAll() {
		return this.getSelectedItems().size() == this.getItems().size();
	}

	@Override
	public void clear() {
		super.clear();
		this.groupMap.clear();
		this.optionMap.clear();
		this.itemMap.clear();
	}
}