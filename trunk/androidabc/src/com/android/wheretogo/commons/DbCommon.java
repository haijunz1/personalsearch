package com.android.wheretogo.commons;

import android.os.Environment;

public final class DbCommon {
	public static final String DATABASE_NAME = "original.db";
	public static final String DATABASE_VERSION = "1";

	public static final String T_PICTURE = "picture";
	public static final String T_TAG = "tag";
	public static final String T_PICTURE_TAG = "pic_tag";
	public static final String T_USER="user";

	public static final String CREAT_PICTURE_TABLE = "create table "
			+ T_PICTURE + " (_id integer primary key autoincrement, "
			+ " name" +
					" text not null, " + " ownerId integer not null, "
			+ " longitude text, " + " latitude text, " + " created text);";

	public static final String CREAT_TAG_TABLE = "create table " + T_TAG
			+ " (_id integer primary key autoincrement, "
			+ " value text not null );";

	public static final String CREAT_PICTURE_TAG_TABLE = "create table "
			+ T_PICTURE_TAG + " (_id integer primary key autoincrement, "
			+ " picId int not null, " + " tagId int not null);";
	public static final String CREAT_USER_TABLE="create table "+T_USER+"(_id " +
			"integer primary key autoincrement,username text);";
}