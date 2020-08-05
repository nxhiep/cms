package com.hiepnx.cms.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.hiepnx.cms.shared.model.Card;
import com.hiepnx.cms.shared.model.Category;
import com.hiepnx.cms.shared.model.IBasic;
import com.hiepnx.cms.shared.model.UserInfo;

public interface DataServiceAsync {

	void loginFromSession(AsyncCallback<UserInfo> callback);

	void logout(AsyncCallback<Void> callback);

	void save(IBasic iBasic, AsyncCallback<IBasic> callback);

	void saves(List<IBasic> iBasics, AsyncCallback<List<IBasic>> callback);

	void delete(IBasic iBasic, AsyncCallback<Void> callback);

	void deletes(List<IBasic> iBasics, AsyncCallback<Void> callback);

	void getCategoriesByParentId(Long parentId, AsyncCallback<List<Category>> callback);

	void getCardsByParentId(Long parentId, AsyncCallback<List<Card>> callback);

	void getCategoryById(Long categoryId, AsyncCallback<Category> callback);

	void countCardsByParentId(Long categoryId, AsyncCallback<Integer> callback);

	void getCardsByParentId(Long categoryId, int offset, int limit, AsyncCallback<List<Card>> callback);

	void saveCard(Card card, AsyncCallback<Card> callback);

	void deleteCard(Card card, AsyncCallback<Void> callback);

}
