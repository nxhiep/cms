package com.hiepnx.cms.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.hiepnx.cms.shared.model.IBasic;

public class ActionEvent extends GwtEvent<ActionEventHandler> {
	public enum Action{
		EDIT, DELETE, ADD, CLICK, VIEW
	}

	public static Type<ActionEventHandler> TYPE = new Type<ActionEventHandler>();
	
	private IBasic iBasic;
	private Action action;
	
	public ActionEvent() {
	}
	
	public ActionEvent(IBasic basic, Action action) {
		this.action = action;
		this.iBasic = basic;
	}
	
	public ActionEvent(IBasic iBasic) {
		this.iBasic = iBasic;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ActionEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ActionEventHandler handler) {
		handler.onEvent(this);
	}
	
	public IBasic getIBasic() {
		return iBasic;
	}
	
	public Action getAction() {
		return action;
	}
}