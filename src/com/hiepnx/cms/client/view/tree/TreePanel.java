package com.hiepnx.cms.client.view.tree;

import java.util.List;

import com.google.gwt.user.client.ui.FlowPanel;
import com.hiepnx.cms.shared.model.IBasic;

public class TreePanel extends FlowPanel {
	
	private TreeOptions options;
	
	public TreePanel(TreeOptions options) {
		this.setStyleName("root-tree-panel");
		this.options = options;
	}
	
	public void updateData(List<IBasic> iBasics) {
		this.clear();
		for (IBasic iBasic : iBasics) {
			this.add(new TreeItem(iBasic, options));
		}
	}
}
