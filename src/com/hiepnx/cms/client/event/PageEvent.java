package com.hiepnx.cms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class PageEvent extends GwtEvent<PageEventHandler> {
	public static Type<PageEventHandler> TYPE = new Type<PageEventHandler>();
	
	private int offset;
	
	public PageEvent(int offset) {
		this.offset = offset;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PageEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PageEventHandler handler) {
		handler.onEvent(this);
	}
	
	public int getOffset() {
		return offset;
	}
}