package com.android.wheretogo.adapter;

import java.util.ArrayList;

import com.android.wheretogo.commons.DbCommon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbAdapter {
	private final Context mCtx;
	private DBHelper mDbHelper;
	private SQLiteDatabase mDb;
	String Tag = "Sqlite";
	private static int version=1;
	private static String t =  "taoen12";
	// inner class
	private static class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, DbCommon.DATABASE_NAME, null, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DbCommon.CREAT_PICTURE_TABLE);
			db.execSQL(DbCommon.CREAT_TAG_TABLE);
			db.execSQL(DbCommon.CREAT_PICTURE_TAG_TABLE);
			db.execSQL(DbCommon.CREAT_USER_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DbCommon.T_PICTURE);
			db.execSQL("DROP TABLE IF EXISTS " + DbCommon.T_TAG);
			db.execSQL("DROP TABLE IF EXISTS " + DbCommon.T_PICTURE_TAG);
			db.execSQL(DbCommon.CREAT_USER_TABLE);
			onCreate(db);
		}
	}

	public DbAdapter(Context ctx) {
		this.mCtx = ctx;
	}

	public DbAdapter open() throws SQLException {
		mDbHelper = new DBHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void clearAll() {
		mDb.execSQL("DROP TABLE IF EXISTS " + DbCommon.T_PICTURE);
		mDb.execSQL("DROP TABLE IF EXISTS " + DbCommon.T_TAG);
		mDb.execSQL("DROP TABLE IF EXISTS " + DbCommon.T_PICTURE_TAG);
		mDb.execSQL("DROP TABLE IF EXISTS " + DbCommon.T_USER);
		mDb.execSQL(DbCommon.CREAT_PICTURE_TABLE);
		mDb.execSQL(DbCommon.CREAT_TAG_TABLE);
		mDb.execSQL(DbCommon.CREAT_PICTURE_TAG_TABLE);
		mDb.execSQL(DbCommon.CREAT_USER_TABLE);

	}

	public void close() {
		mDbHelper.close();
	}

	public long insertPicture(String picname, int ownerId, String longitude,
			String latitude, String createdTime) throws SQLException {
		ContentValues args = new ContentValues();
		args.put("name", picname);
		args.put("ownerId", ownerId);
		args.put("longitude", longitude);
		args.put("latitude", latitude);
		args.put("created", createdTime);
		return mDb.insert(DbCommon.T_PICTURE, null, args);
	}
	public long insertBasicPicInfo(String picname, int ownerId, String longitude,
			String latitude, String createdTime) throws SQLException {
		//5.20 created by Li Runsheng
		ContentValues args = new ContentValues();
//		args.put("_id", picId);
		args.put("name", picname);
		args.put("ownerId", ownerId);
		args.put("longitude", longitude);
		args.put("latitude", latitude);
		args.put("created", createdTime);
		return mDb.insert(DbCommon.T_PICTURE, null, args);
	}
	
	//2011.6.7 lirunsheng
	public long insertUser(int userId,String userName)throws SQLException{
		ContentValues args = new ContentValues();
		args.put("_id", userId);
		args.put("username", userName);
		return mDb.insert(DbCommon.T_USER, null, args);
	}

	public boolean deletePicture(int picId) {
		return mDb.delete(DbCommon.T_PICTURE, "_id" + "=" + picId, null) > 0;
	}

	public Cursor getAllPictures() {
		return mDb.query(DbCommon.T_PICTURE, new String[] { "_id", "name",
				"ownerId", "longitude", "latitude", "created" }, null, null,
				null, null, null);
	}
    public String getOwner(int ownerID){
    	String owner=null;
    	Cursor cursor =mDb.query("user", new String[]{"username"}, null, null, null, null, null);
    	if (cursor.moveToFirst() != false) {
			final int ownerIndex = cursor.getColumnIndex("username");
			owner = cursor.getString(ownerIndex);
		}
    	cursor.close();
    	return owner;
    }
    public String getOwnerName(int ownerID){
    	String owner=null;
    	Cursor cursor =mDb.query("user", new String[]{"username"}, "_id="+ownerID, null, null, null, null);
    	if (cursor.moveToFirst() != false) {
			final int ownerIndex = cursor.getColumnIndex("username");
			owner = cursor.getString(ownerIndex);
		}
    	cursor.close();
    	return owner;
    }
	public Cursor getAllLongitudeAndLatitude(){
		return mDb.query(true,DbCommon.T_PICTURE, new String[]{"longitude","latitude"},
				null, null, null, null, null,null);
		
		
	}

	public Cursor getSitePictures(int siteLongitude, int siteLatitude,
			int halfSide) {
		// double siteLon = Double.parseDouble(siteLongitude);
		// double siteLat = Double.parseDouble(siteLatitude);
		// double hS = Double.parseDouble(halfSide);
		//		
		String lowLon = String.valueOf(siteLongitude - halfSide);
		String highLon = String.valueOf(siteLongitude + halfSide);
		String lowLat = String.valueOf(siteLatitude - halfSide);
		String highLat = String.valueOf(siteLatitude + halfSide);

		return mDb.query(DbCommon.T_PICTURE, new String[] { "_id" },
				"?<longitude<? and ?<latitude<?", new String[] { lowLon,
						highLon, lowLat, highLat }, null, null, null);
	}

	public Cursor getPicture(int picId) throws SQLException {

		Cursor mCursor = mDb.query(true, DbCommon.T_PICTURE, new String[] {
				"_id", "name", "ownerId", "longitude", "latitude", "created" },
				"_id = " + picId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public String getLati(int picId) {
		String lati = null;
		Cursor mCursor = mDb.query(true, DbCommon.T_PICTURE,
				new String[] { "latitude" }, "_id = " + picId, null, null,
				null, null, null);
		if (mCursor.moveToFirst() != false) {
			final int latiIndex = mCursor.getColumnIndex("latitude");
			lati = mCursor.getString(latiIndex);
		}
		mCursor.close();
		return lati;

	}

	public String getLongi(int picId) {
		String longi = null;
		Cursor mCursor = mDb.query(true, DbCommon.T_PICTURE,
				new String[] { "longitude" }, "_id = " + picId, null, null,
				null, null, null);
		if (mCursor.moveToFirst() != false) {
			final int longiIndex = mCursor.getColumnIndex("longitude");
			longi = mCursor.getString(longiIndex);
		}
		mCursor.close();
		return longi;
	}

	public String getPictureNameByPicId(int picId) throws SQLException {

		String name = null;
		Cursor mCursor = mDb.query(true, DbCommon.T_PICTURE,
				new String[] { "name" }, "_id = " + picId, null, null, null,
				null, null);
		if (mCursor.moveToFirst() != false) {
			final int nameIndex = mCursor.getColumnIndex("name");
			name = mCursor.getString(nameIndex);
		}
		mCursor.close();
		return name;
	}
	public int getPictureIdByPicName(String picName)throws SQLException{
		int id = -1;
		Cursor mCursor = mDb.query(true, DbCommon.T_PICTURE, new String[]{"_id"}, "name='"+picName+"'", null, null, null,null,null);
		if(mCursor.moveToFirst() != false){
			final int idIndex = mCursor.getColumnIndex("_id");
			id = mCursor.getInt(idIndex);
		}
		mCursor.close();
		return id;
	}

	public boolean updatePicture(int picId, String picname, int ownerId,
			String longitude, String latitude, String createdTime)
			throws SQLException {
		ContentValues args = new ContentValues();
		args.put("name", picname);
		args.put("ownerId", ownerId);
		args.put("longitude", longitude);
		args.put("latitude", latitude);
		args.put("created", createdTime);
		return mDb.update(DbCommon.T_PICTURE, args, "_id = " + picId, null) > 0;
	}

	// methods(insert, delete, query, update) for table_tag
	public int insertTag(String tag) throws SQLException {
		ContentValues args = new ContentValues();
		args.put("value", tag);
		return (int)mDb.insert(DbCommon.T_TAG, null, args);
	}

	public boolean isTagExist(String tag) {
		Cursor c = null;
		boolean flag = false;
		c = mDb.query(DbCommon.T_TAG, new String[] { "_id", "value", },
				"value=" + "\"" + tag + "\"", null, null, null, null);
		if (c.moveToFirst() != false)
			flag = true;
		c.close();
		return flag;
	}

	public boolean isPic_TagExist(int picId, int tagId) {
		Cursor c = null;
		boolean flag = false;
		c = mDb.query(DbCommon.T_PICTURE_TAG, new String[] { "_id" }, "picId="
				+ picId + " and " + "tagId=" + tagId, null, null, null, null);
		if (c.moveToFirst() != false)
			flag = true;
		c.close();
		return flag;
	}

	public boolean isTagRelatExist(int tagId) {
		Cursor c = null;
		boolean flag = false;
		c = mDb.query(DbCommon.T_PICTURE_TAG, new String[] { "_id" }, "tagId="
				+ tagId, null, null, null, null);
		if (c.moveToFirst() != false)
			flag = true;
		c.close();
		return flag;
	}
	
	//2011.6.7 lirunsheng
	public boolean isUserIdExist(int userId){
		Cursor c = null;
		boolean flag = false;
		c = mDb.query(DbCommon.T_USER, new String[]{"_id"}, "_id="+userId, null, null, null, null);
		if(c.moveToFirst()!=false){
			flag = true;
		}
		c.close();
		return flag;
	}

	public boolean deleteTag(int tagId) {
		return mDb.delete(DbCommon.T_TAG, "_id" + "=" + tagId, null) > 0;
	}

	public Cursor getAllTags() {
		return mDb.query(DbCommon.T_TAG, new String[] { "_id", "value", },
				null, null, null, null, null);
	}

	public int getTagIdByTag(String tag) {
		int tagId = 0;
		Cursor c = null;
		c = mDb.query(DbCommon.T_TAG, new String[] { "_id", "value", },
				"value=" + "\"" + tag + "\"", null, null, null, null);

		if (c.moveToFirst() != false) {
			final int tagIdIndex = c.getColumnIndex("_id");
			tagId = c.getInt(tagIdIndex);
		}
		c.close();
		return tagId;
	}

	public String getTagbByTagId(int tagId) throws SQLException {

		String tagName = null;
		Cursor mCursor = mDb.query(true, DbCommon.T_TAG, new String[] { "_id",
				"value", }, "_id = " + tagId, null, null, null, null, null);
		if (mCursor.moveToFirst() != false) {
			final int tagNameIndex = mCursor.getColumnIndex("value");
			tagName = mCursor.getString(tagNameIndex);
		}
		mCursor.close();
		return tagName;
	}

	public boolean updateTag(int tagId, String tagvalue) throws SQLException {
		ContentValues args = new ContentValues();
		args.put("value", tagvalue);
		return mDb.update(DbCommon.T_TAG, args, "_id = " + tagId, null) > 0;
	}

	public long insertPicTagById(int picId, int tagId) throws SQLException {
		ContentValues args = new ContentValues();
		args.put("picId", picId);
		args.put("tagId", tagId);
		return mDb.insert(DbCommon.T_PICTURE_TAG, null, args);
	}

	public long insertPicTagByPicName(String name, String tag)
			throws SQLException {
		ContentValues args = new ContentValues();
		args.put("name", name);
		args.put("value", tag);
		return mDb.insert(DbCommon.T_PICTURE_TAG, null, args);
	}

	public boolean deletePicTag(int picId) {
		return mDb.delete(DbCommon.T_PICTURE_TAG, "_id" + "=" + picId, null) > 0;
	}

	public Cursor getAllPicTags() {
		return mDb.query(DbCommon.T_PICTURE_TAG, new String[] { "_id", "picId",
				"tagId" }, null, null, null, null, null);
	}

	public ArrayList<String> getPicTagsByPicId(int picId) throws SQLException {
		ArrayList<String> tags = null;
		Cursor mCursor = mDb.query(true, DbCommon.T_PICTURE_TAG, new String[] {
				"_id", "picId", "tagId" }, "picId =" + picId, null, null, null,
				null, null);
		if (mCursor.moveToFirst() != false) {
			final int tagIdIndex = mCursor.getColumnIndex("tagId");
			tags = new ArrayList<String>();
			for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor
					.moveToNext()) {
				int tagId = mCursor.getInt(tagIdIndex);
				tags.add(getTagbByTagId(tagId));
			}
		}

		return tags;
	}

	public Cursor getPicTagByName(String path) throws SQLException {
		Cursor mCursor = mDb.query(true, DbCommon.T_PICTURE_TAG, new String[] {
				"_id", "picId", "tagId" }, "name = " + path, null, null, null,
				null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public boolean updatePicTag(int pictagId, int picId, int tagId)
			throws SQLException {
		ContentValues args = new ContentValues();
		args.put("picId", picId);
		args.put("tagId", tagId);
		return mDb.update(DbCommon.T_PICTURE_TAG, args, "_id = " + pictagId,
				null) > 0;
	}

	public void sqlExecute(String sql) {
		mDb.execSQL(sql);
	}

	public void updateAll() {
		mDbHelper.onUpgrade(mDbHelper.getWritableDatabase(), 1, 2);
	}	

}
