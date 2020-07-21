package com.hiepnx.cms.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.hiepnx.cms.client.view.Toaster;
import com.hiepnx.cms.shared.model.IBasic;
import com.hiepnx.cms.shared.model.UserInfo;

public class ClientData {
	public final static DataServiceAsync DATA_SERVICE = GWT.create(DataService.class);
	
	public static void prepareData() {
		
	}
	
	public static void loginFromSession(AsyncCallback<UserInfo> callback) {
		DATA_SERVICE.loginFromSession(new AsyncCallback<UserInfo>() {
			
			@Override
			public void onSuccess(UserInfo result) {
				LoginManager.setCurrentUser(result);
				if(callback != null) {
					callback.onSuccess(result);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				LoginManager.setCurrentUser(null);
				if(callback != null) {
					callback.onFailure(caught);
				}
			}
		});
	}
	
	public static void save(IBasic iBasic, AsyncCallback<IBasic> callback) {
		DATA_SERVICE.save(iBasic, new AsyncCallback<IBasic>() {
			
			@Override
			public void onSuccess(IBasic result) {
				Toaster.showSuccess("Lưu thành công!");
				if(callback != null) {
					callback.onSuccess(result);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Toaster.showError("Có lỗi xảy ra, thử lại!");
				if(callback != null) {
					callback.onFailure(caught);
				}
			}
		});
	}
	
	public static void saves(List<IBasic> iBasics, AsyncCallback<List<IBasic>> callback) {
		DATA_SERVICE.saves(iBasics, new AsyncCallback<List<IBasic>>() {
			
			@Override
			public void onSuccess(List<IBasic> result) {
				Toaster.showSuccess("Lưu thành công!");
				if(callback != null) {
					callback.onSuccess(result);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Toaster.showError("Có lỗi xảy ra, thử lại!");
				if(callback != null) {
					callback.onFailure(caught);
				}
			}
		});
	}
	
	public static void delete(IBasic iBasic, AsyncCallback<Void> callback) {
		DATA_SERVICE.delete(iBasic, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				Toaster.showSuccess("Xoá thành công!");
				if(callback != null) {
					callback.onSuccess(result);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Toaster.showError("Có lỗi xảy ra, thử lại!");
				if(callback != null) {
					callback.onFailure(caught);
				}
			}
		});
	}
	
	public static void deletes(List<IBasic> iBasics, AsyncCallback<Void> callback) {
		DATA_SERVICE.deletes(iBasics, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				Toaster.showSuccess("Lưu thành công!");
				if(callback != null) {
					callback.onSuccess(result);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Toaster.showError("Có lỗi xảy ra, thử lại!");
				if(callback != null) {
					callback.onFailure(caught);
				}
			}
		});
	}
}
