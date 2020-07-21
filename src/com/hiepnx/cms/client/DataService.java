package com.hiepnx.cms.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.hiepnx.cms.shared.model.Card;
import com.hiepnx.cms.shared.model.Category;
import com.hiepnx.cms.shared.model.IBasic;
import com.hiepnx.cms.shared.model.UserInfo;

@RemoteServiceRelativePath("api")
public interface DataService extends RemoteService {

	UserInfo loginFromSession();

	void logout();

	IBasic save(IBasic iBasic);

	List<IBasic> saves(List<IBasic> iBasics);

	void delete(IBasic iBasic);

	void deletes(List<IBasic> iBasics);

	List<Category> getCategoriesByParentId(Long parentId);

	List<Card> getCardsByParentId(Long parentId);
}
