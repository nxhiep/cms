package com.hiepnx.cms.server.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.hiepnx.cms.shared.Config;
import com.hiepnx.cms.shared.model.IBasic;

public class DAO {
	public static final Logger log = Logger.getLogger(DAO.class.getName());

	public IBasic save(IBasic iBasic) {
		long currentTime = new Date().getTime();
		Object id = iBasic.getId();
		boolean checkNewObject = (id instanceof Long && (Long) id == Config.NULL_ID) 
				|| (id instanceof Integer && (Integer) id == Config.INT_NULL)
				|| (id instanceof Double && (Double) id == Config.NULL_ID);
		if(id == null || checkNewObject) {
			iBasic.setCreateDate(currentTime);
		}
		iBasic.setLastUpdate(currentTime);
		ofy().save().entity(iBasic).now();
		return iBasic;
	}
	
	public List<IBasic> saves(List<IBasic> iBasics) {
		long currentTime = new Date().getTime();
		for (IBasic iBasic : iBasics) {
			Object id = iBasic.getId();
			boolean checkNewObject = (id instanceof Long && (Long) id == Config.NULL_ID) 
				|| (id instanceof Integer && (Integer) id == Config.INT_NULL)
				|| (id instanceof Double && (Double) id == Config.NULL_ID);
			if(id == null || checkNewObject) {
				iBasic.setCreateDate(currentTime);
			}
			iBasic.setLastUpdate(currentTime);
		}
		ofy().save().entity(iBasics).now();
		return iBasics;
	}
	
	public void delete(IBasic iBasic) {
		ofy().delete().entity(iBasic);
	}
	
	public void deletes(List<IBasic> iBasics) {
		ofy().delete().entities(iBasics);
	}
}
