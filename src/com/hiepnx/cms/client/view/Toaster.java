package com.hiepnx.cms.client.view;

import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.extras.notify.client.constants.NotifyPlacement;
import org.gwtbootstrap3.extras.notify.client.constants.NotifyType;
import org.gwtbootstrap3.extras.notify.client.ui.Notify;
import org.gwtbootstrap3.extras.notify.client.ui.NotifySettings;

public class Toaster {
	// new
	public static void showToaster(String message, NotifyType notifyType, IconType iconType) {
		NotifySettings settings = NotifySettings.newSettings(); 
		settings.setType(notifyType);
		settings.setDelay(2000);
		settings.setZIndex(9999999);
		settings.setPlacement(NotifyPlacement.TOP_CENTER);
		Notify.notify("&nbsp;&nbsp;", message, iconType, settings);
	}
	
	public static void showInfo(String message) {
		showToaster(message, NotifyType.INFO, IconType.INFO);
	}
	
	public static void showSuccess(String message) {
		showToaster(message, NotifyType.SUCCESS, IconType.CHECK);
	}
	
	public static void showWarning(String message) {
		showToaster(message, NotifyType.WARNING, IconType.EXCLAMATION);
	}
	
	public static void showError(String message) {
		showToaster(message, NotifyType.DANGER, IconType.TIMES_CIRCLE);
	}
}
