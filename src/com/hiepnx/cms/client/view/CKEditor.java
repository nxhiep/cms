package com.hiepnx.cms.client.view;

import org.gwtbootstrap3.extras.summernote.client.ui.Summernote;

import com.google.gwt.user.client.ui.FlowPanel;

public class CKEditor extends FlowPanel {

	private Summernote summerNote = new Summernote();

	public CKEditor() {
		this(-1);
	}
	
	public CKEditor(final int heightContent) {
		if(heightContent > 0) {
			this.summerNote.setMinHeight(heightContent);
		}
		this.add(summerNote);
	}

	public void setHeight(int height) {
		summerNote.setMinHeight(height);
	}

	public String getContent() {
		return summerNote.getCode();
	}

	public void setContent(final String content) {
		this.summerNote.setCode(content);
	}

	public Summernote getSummernote() {
		return this.summerNote;
	}
}