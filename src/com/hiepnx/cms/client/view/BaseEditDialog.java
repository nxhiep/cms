package com.hiepnx.cms.client.view;

import com.hiepnx.cms.shared.Callback;
import com.hiepnx.cms.shared.model.IBasic;

public class BaseEditDialog extends MyDialog {
	
	protected IBasic iBasic;
	
	public BaseEditDialog() {
		super();
	}
	
	public void show(String title, IBasic iBasic, Callback<Boolean> callback) {
		updateData(iBasic);
		super.show(title, null, "Save", "Close", callback);
	}

	protected void updateData(IBasic iBasic) {
		this.iBasic = iBasic;
	}
	
	public IBasic getCaterory() {
		return iBasic;
	}
}