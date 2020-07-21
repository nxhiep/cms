package com.hiepnx.cms.shared.model;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public interface IBasic extends Serializable, IsSerializable {
	Object getId();
	void setLastUpdate(Long lastUpdate);
	void setCreateDate(Long createDate);
}
