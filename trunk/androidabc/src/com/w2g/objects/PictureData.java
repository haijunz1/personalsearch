package com.w2g.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class PictureData implements Serializable {
	//This class conclude all the info of a pic.
//	private int _id; // picture's id
//	private String name;
//	private String ownerId;
//	private String longitude;
//	private String latitude;
//	private String created;
	private ArrayList<String> basicPicInfo;
	private ArrayList<String> tags; // maybe not only one tag
	private byte[] picture; // content of pic;


	public ArrayList<String> getBasicPicInfo() {
		return basicPicInfo;
	}

	public void setBasicPicInfo(ArrayList<String> basicPicInfo) {
		this.basicPicInfo = basicPicInfo;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
}
