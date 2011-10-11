package com.w2g.objects;

import java.io.Serializable;

public class ClassSuccess implements Serializable {
	private boolean success;
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
