package com.w2g.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PicIdsUserInfoClientNeed implements Serializable{
	private ArrayList<Integer> picIds = new ArrayList<Integer>();
	private Map userInfo = new HashMap();
	public Map getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(Map userInfo) {
		this.userInfo = userInfo;
	}

	public ArrayList<Integer> getPicIds() {
		return picIds;
	}

	public void setPicIds(ArrayList<Integer> picIds) {
		this.picIds = picIds;
	}
	
}
