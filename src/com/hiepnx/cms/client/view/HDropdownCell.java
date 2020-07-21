package com.hiepnx.cms.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class HDropdownCell extends AbstractCell<Integer> {

	private String titleHTML;
	private Long time = new Date().getTime();
	private List<String> items = new ArrayList<String>();
	private String keyClick = "dropdown-item";

	public HDropdownCell(List<String> items) {
		this("<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>", items);
	}

	public HDropdownCell(String titleHTML, List<String> items) {
		super(BrowserEvents.CLICK);
		this.titleHTML = titleHTML;
		if(items != null) {
			this.items = items;
		}
	}

	@Override
	public void render(Context context, Integer value, SafeHtmlBuilder sb) {
		sb.appendHtmlConstant("<div class=\"dropdown\">");
		sb.appendHtmlConstant(
				"<button class=\"btn btn-secondary dropdown-toggle\" type=\"button\" id=\"dropdownMenuButton-" + time
						+ "\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">" + this.titleHTML
						+ "</button>");
		sb.appendHtmlConstant("<div class=\"dropdown-menu dropdown-menu-right\" aria-labelledby=\"dropdownMenuButton-" + time + "\">");
		for (int index = 0; index < items.size(); index++) {
			sb.appendHtmlConstant("<button class=\""+keyClick+"\" data-index=\""+index+"\">" + items.get(index) + "</button>");
		}
		sb.appendHtmlConstant("</div>");
		sb.appendHtmlConstant("</div");
	}
	
	@Override
	public void onBrowserEvent(Context context, Element parent, Integer value, NativeEvent event,
			ValueUpdater<Integer> valueUpdater) {
		super.onBrowserEvent(context, parent, value, event, valueUpdater);
		if (BrowserEvents.CLICK.equals(event.getType())) {
			EventTarget eventTarget = event.getEventTarget();
			Element element = Element.as(eventTarget);
			if(element.getClassName().contains(keyClick)) {
				String indexStr = element.getAttribute("data-index");
				int index = -1;
				try {
					index = Integer.parseInt(indexStr);
				} catch(Exception e) {}
				valueUpdater.update(index);
			}
		}
	}
}