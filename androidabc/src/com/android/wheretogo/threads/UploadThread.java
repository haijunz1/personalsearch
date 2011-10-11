package com.android.wheretogo.threads;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.android.wheretogo.adapter.DbAdapter;
import com.android.wheretogo.constant.Constant;
import com.android.wheretogo.setconnection.SetHttpURLConnection;
import com.android.wheretogo.util.JsonContent;
import com.w2g.objects.ClassSuccess;
import com.w2g.objects.PictureData;
import com.w2g.operateObject.OperateObject;

public class UploadThread extends Thread {
	private String uri;
	private int picId;
	private String picDirectory;
	private Context context;
	private boolean uploadSuccess;
	private Handler handler;

	public UploadThread(String uri, int picId, String picDirectory,
			Context context, Handler handler) {
		this.uri = uri;
		this.picId = picId;
		this.context = context;
		this.picDirectory = picDirectory;
		this.handler = handler;
	}

	public void run() {
		Looper.prepare();
		try {
			/*先上传图片内容，在上传图片的其他相关信息*/
			ArrayList<String> basicPicInfo = new ArrayList<String>();
			ArrayList<String> tags = new ArrayList<String>();
			DbAdapter adapter = new DbAdapter(context);
			adapter.open();
			Cursor cursor = adapter.getPicture(picId);// get basic info of a pic
			for (int i = 0; i < 6; i++) {
				basicPicInfo.add(cursor.getString(i));
			}
			tags = adapter.getPicTagsByPicId(picId);// get tags of the pic
			File file = new File(picDirectory + cursor.getString(0) + ".jpg");
			if (file.length() == 0) {
				file = new File(basicPicInfo.get(1));
			}
			String picNameAtAndroid = basicPicInfo.get(1);
			String picIdAtServer = picNameAtAndroid.substring(picNameAtAndroid
					.lastIndexOf("/") + 1);

			FileEntity entity = new FileEntity(file, "binary/octet-stream");
			HttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(Constant.urlIMP
					+ "/uploadPictureContentFromAndroid.action?picId="
					+ picIdAtServer);
			HttpResponse response = null;
			httpPost.setEntity(entity);
			response = client.execute(httpPost);
			
			/*发图片的附属信息*/
			String strTags = null;
			if(tags!=null){
				strTags = "";
				for(String tag:tags){
					strTags += tag + Constant.tagSeperator;
				}
			}
			
			String urlPath = Constant.urlIMP+"/uploadPictureAttachedInformation.action";
			urlPath += "?picId=" + picIdAtServer + "&tags=" + strTags  ;
			urlPath += "&ownerId=" + basicPicInfo.get(2) + "&longitude=" + basicPicInfo.get(3) ;
			urlPath += "&latitude=" + basicPicInfo.get(4);
			
			HttpGet httpGet = new HttpGet(urlPath);
			String json = JsonContent.getContent(httpGet);
			JSONObject jsonObject = new JSONObject(json);
			boolean success = jsonObject.getBoolean("success");
			if(success){
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		} catch (Exception e) {
			Log.d("bad",e.getMessage());
		}
/*
		// setup connection
		SetHttpURLConnection setHUC = new SetHttpURLConnection();
		HttpURLConnection httpURLConnection = setHUC.setHttpURLConnection(uri
				+ "/uploadPicture.do");

		// get all pic info
		ArrayList<String> basicPicInfo = new ArrayList<String>();
		ArrayList<String> tags = new ArrayList<String>();
		byte[] picture = null;
		try {
			DbAdapter adapter = new DbAdapter(context);
			adapter.open();
			Cursor cursor = adapter.getPicture(picId);// get basic info of a pic
			for (int i = 0; i < 6; i++) {
				basicPicInfo.add(cursor.getString(i));
			}
			tags = adapter.getPicTagsByPicId(picId);// get tags of the pic
			File file = new File(picDirectory + cursor.getString(0) + ".jpg");
			if (file.length() == 0) {
				file = new File(basicPicInfo.get(1));
			}
			FileInputStream fileInputStream = new FileInputStream(file);
			picture = new byte[(int) file.length()];
			fileInputStream.read(picture);// get content of a pic
			cursor.close();
			adapter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// set pictureData object
		OutputStream outputStream = null;
		try {
			outputStream = httpURLConnection.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PictureData pictureData = new PictureData();
		pictureData.setBasicPicInfo(basicPicInfo);
		pictureData.setTags(tags);
		pictureData.setPicture(picture);

		// write
		OperateObject operateObject = new OperateObject();
		operateObject.writeObjectIntoOutStream(pictureData, outputStream);

		try {
			InputStream inputStream = httpURLConnection.getInputStream();
			ClassSuccess uploadSuccess = (ClassSuccess) operateObject
					.readObjectFromInputStream(inputStream);
			if (uploadSuccess.isSuccess()) { // send msg to UI
				// Thread(egActivityCameraImageShow)
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		httpURLConnection.disconnect();
*/
	}
}
