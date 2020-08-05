package com.hiepnx.cms.server;

import java.util.List;

import com.hiepnx.cms.client.DataService;
import com.hiepnx.cms.server.dao.CardDAO;
import com.hiepnx.cms.server.dao.CategoryDAO;
import com.hiepnx.cms.server.dao.UserDAO;
import com.hiepnx.cms.shared.model.Card;
import com.hiepnx.cms.shared.model.Category;
import com.hiepnx.cms.shared.model.IBasic;
import com.hiepnx.cms.shared.model.UserInfo;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DataServiceImpl extends CustomRemoteServiceServlet implements DataService {

	protected UserDAO USER_DAO = new UserDAO();
	protected CategoryDAO CATEGORY_DAO = new CategoryDAO();
	protected CardDAO CARD_DAO = new CardDAO();
	
	@Override
	public IBasic save(IBasic iBasic) {
		return USER_DAO.save(iBasic);
	}
	
	@Override
	public List<IBasic> saves(List<IBasic> iBasics) {
		return USER_DAO.saves(iBasics);
	}
	
	@Override
	public void delete(IBasic iBasic) {
		USER_DAO.delete(iBasic);
	}
	
	@Override
	public void deletes(List<IBasic> iBasics) {
		USER_DAO.deletes(iBasics);
	}
	
	@Override
	public UserInfo loginFromSession() {
		return USER_DAO.loginFromSession(this.getThreadLocalRequest(), this.getThreadLocalResponse());
	}

	@Override
	public void logout() {
		USER_DAO.logout(this.getThreadLocalRequest(), this.getThreadLocalResponse());
	}
	
	@Override
	public List<Category> getCategoriesByParentId(Long parentId) {
		return CATEGORY_DAO.getCategoriesByParentId(parentId);
	}
	
	@Override
	public List<Card> getCardsByParentId(Long parentId) {
		return CARD_DAO.getCardsByParentId(parentId);
	}

	@Override
	public Category getCategoryById(Long categoryId) {
		return CATEGORY_DAO.getCategoryById(categoryId);
	}

	@Override
	public int countCardsByParentId(Long categoryId) {
		return CARD_DAO.countCardsByParentId(categoryId);
	}
	
	@Override
	public List<Card> getCardsByParentId(Long categoryId, int offset, int limit) {
		return CARD_DAO.getCardsByParentId(categoryId, offset, limit);
	}

	@Override
	public void deleteCard(Card card) {
		CARD_DAO.deleteCard(card);
	}

	@Override
	public Card saveCard(Card card) {
		return CARD_DAO.saveCard(card);
	}
}
