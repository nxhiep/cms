package com.hiepnx.cms.client.view;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.ui.FlowPanel;

public class AvatarPanel extends FlowPanel {
	public static final String DEFAULT_AVATAR = "/resources/images/default_avatar.png";
	public AvatarPanel() {
		this.setStyleName("img-avatar gwt-avatar");
		this.setSize("40px", "40px");
	}
	
	public void initAvatar(String userId) {
		initAvatar(userId, null, null, null);
	}
	
	public void initAvatar(String userId, String defaultAvatar, String width, String height) {
		if(width != null && !width.isEmpty()) {
			this.setWidth(width);
		}
		if(height != null && !height.isEmpty()) {
			this.setHeight(height);
		}
		this.getElement().setAttribute("data-id", userId);
		if(defaultAvatar == null || defaultAvatar.isEmpty()) {
			defaultAvatar = DEFAULT_AVATAR;
		}
		this.getElement().setAttribute("data-root-url", defaultAvatar);
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				initAvatar();
			}
		});
	}
	
	private native void initAvatar() /*-{
		typeof $wnd.checkAvatar !== 'undefined' && $wnd.checkAvatar('.gwt-avatar');
	}-*/;
}
