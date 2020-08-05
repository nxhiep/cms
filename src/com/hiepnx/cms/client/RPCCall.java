package com.hiepnx.cms.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestTimeoutException;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.hiepnx.cms.client.view.LoadingPage;
import com.hiepnx.cms.client.view.Toaster;

public abstract class RPCCall<T> implements AsyncCallback<T> {
	protected abstract void callService(AsyncCallback<T> callback);

	private void call(final int retriesLeft, final boolean isModal) {
		onRPCOut(isModal);
		callService(new AsyncCallback<T>() {
			@Override
			public void onSuccess(final T result) {
				onRPCIn(isModal);
				RPCCall.this.onSuccess(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				onRPCIn(isModal);
				GWT.log(caught.toString(), caught);
				try {
					throw caught;
				} catch (InvocationException invocationException) {
					if (retriesLeft <= 0) {
						RPCCall.this.onFailure(invocationException);
					} else {
						call(retriesLeft - 1, isModal); // retry call
					}
				} catch (IncompatibleRemoteServiceException remoteServiceException) {
					Toaster.showError("This app is out of date, please update to the newest version!");
				} catch (SerializationException serializationException) {
					Toaster.showError("A serialization error occurred. Please try again later!");
				} catch (RequestTimeoutException e) {
					Toaster.showError("This is taking too long, try again!");
				} catch (Throwable e) {
					Toaster.showError("Application failed! " + e.getMessage());
					RPCCall.this.onFailure(e);
				}
			}
		});
	}

	private void onRPCIn(boolean isModal) {
		if(isModal){
			LoadingPage.hide();
		}
	}

	private void onRPCOut(boolean isModal) {
		if(isModal){
			LoadingPage.show();
		}
	}

	public void retry(int retryCount) {
		retry(retryCount, false);
	}

	public void retry(int retryCount, boolean isModal) {
		call(retryCount, isModal);
	}
}