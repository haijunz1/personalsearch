package com.android.wheretogo.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;

import com.android.wheretogo.adapter.NewArrayAdapter;
import com.android.wheretogo.constant.Constant;
import com.android.wheretogo.setconnection.SetHttpURLConnection;
import com.android.wheretogo.util.JsonContent;
import com.w2g.objects.ClassSuccess;
import com.w2g.objects.User;
import com.w2g.operateObject.OperateObject;

public class ActivityLogin extends Activity {
	AutoCompleteTextView editTextAccount = null;
	EditText editTextPassword = null;
	Button buttonLogin = null;
	Button buttonRegister = null;
	ImageButton buttonSelectAccount = null;
	CheckBox checkBox = null;
	OnClickListener clickListenerLogin = null;
	OnClickListener clickListenerRegister = null;
	OnClickListener clickListenerSelectAccount = null;
	SharedPreferences userInfoPref = null;
	SharedPreferences lastAccountPref = null;
	String accountKey = "ACCOUNT_KEY";
	String passwordKey = "PASSWORD_KEY";
	ArrayList<String> arrayListAccount = null;
//	String uri = "http://203.91.121.3:8085/ServerW2G";
	String uri = Constant.uri;
	public static final String USERINFO = "userInformation";
	public static final String LASTACCOUNT = "lastAccount";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("Welcome!");
		setContentView(R.layout.login);

		buttonLogin = (Button) findViewById(R.id.login_btn_login);
		buttonRegister = (Button) findViewById(R.id.button1);
		buttonSelectAccount = (ImageButton) findViewById(R.id.ImageButton02);
		editTextAccount = (AutoCompleteTextView) findViewById(R.id.login_edit_account);
		editTextPassword = (EditText) findViewById(R.id.login_edit_pwd);
		checkBox = (CheckBox) findViewById(R.id.login_cb_savepwd);
		editTextAccount.setText(getLastAccout());
		editTextPassword.setText(getLastPassword());

		userInfoPref = getSharedPreferences(USERINFO, MODE_PRIVATE);
		Map map = userInfoPref.getAll();
		arrayListAccount = new ArrayList<String>(map.keySet());

		NewArrayAdapter adapter = new NewArrayAdapter(this, arrayListAccount);
		editTextAccount.setAdapter(adapter);
		editTextAccount.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});
		clickListenerLogin = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Map map = userInfoPref.getAll();
				Object[] array = map.keySet().toArray();
				SharedPreferences.Editor editor = userInfoPref.edit();
				for (Object key : array) {
					if (!arrayListAccount.contains(key)) {
						editor.remove(key.toString());
					}
				}
				editor.commit();

				if (judgeCanLogin()) {
					setPresentAccount();// 保存现在的账号
					processPassword();// 是否保存密码
					Intent intent = new Intent(ActivityLogin.this,
							ActivityMain.class);
					startActivity(intent);
					ActivityLogin.this.finish();
				}
				else{
					new AlertDialog.Builder(ActivityLogin.this)
					.setTitle("错误")
					.setMessage("用户名或密码错误")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}).show();
				}
			}
		};
		clickListenerSelectAccount = new OnClickListener() {
			@Override
			public void onClick(View v) {
				editTextAccount.showDropDown();
				editTextAccount.setThreshold(1000);
			}
		};
		clickListenerRegister = new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActivityLogin.this,ActivityRegister.class);
				startActivity(intent);
			}
		};
		buttonSelectAccount.setOnClickListener(clickListenerSelectAccount);
		buttonLogin.setOnClickListener(clickListenerLogin);
		buttonRegister.setOnClickListener(clickListenerRegister);
	}

	private boolean judgeCanLogin() {
		/*
		String userId = editTextAccount.getText().toString();
		String password = editTextPassword.getText().toString();
		if (userId.equals("") || password.equals("")) {
			return false;
		} else {
			return (userIdPasswordRight(userId,password))?true:false;
			
		}
		*/
		
		String userEmail = editTextAccount.getText().toString();
		String password = editTextPassword.getText().toString();
		if (userEmail.equals("") || password.equals("")) {
			return false;
		} else {
			return (userIdPasswordRight2(userEmail,password));
		}
	}

	/*add by lrs 2011.10.3*/
	private boolean userIdPasswordRight2(String userEmail, String password){
		String url = Constant.urlIMP + "/loginFromAndroid.action";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", userEmail));
		params.add(new BasicNameValuePair("password",password));
		params.add(new BasicNameValuePair("validationCode","#android#"));
		HttpPost request = new HttpPost();
		boolean success = false;
		try {
			request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			String json = JsonContent.getContent(new HttpGet(url+"?email="+userEmail+"&password="+password));
			JSONObject jsonObject = new JSONObject(json);
			success = jsonObject.getBoolean("success");
			Constant.userName = jsonObject.getString("userName");
			Constant.useId = jsonObject.getString("userId");
		} catch (UnsupportedEncodingException e1) {
			Log.d("bad", e1.getMessage());
		} catch (Exception e) {
			Log.d("bad", e.toString());
		}
		return success;
	}
	private boolean userIdPasswordRight(String userId, String password) {
		int intUserId;
		try{
			intUserId = Integer.parseInt(userId);
		}catch(NumberFormatException e){
			return false;
		}
		//set connection
		SetHttpURLConnection setHUC = new SetHttpURLConnection();
		HttpURLConnection httpURLConnection = setHUC
				.setHttpURLConnection(uri + "/login.do");
		
		//write User into outputstream;
		User user = new User();
		user.setUserId(intUserId);
		user.setPassword(password);
		OperateObject operateObject = new OperateObject();
		try {
			operateObject.writeObjectIntoOutStream(user, httpURLConnection.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//read LoginSuccess;
		ClassSuccess loginSuccess = null;
		try {
			Object object = operateObject.readObjectFromInputStream(httpURLConnection.getInputStream());
			loginSuccess = (ClassSuccess)object;
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean success = loginSuccess.isSuccess();
		Constant.userName = loginSuccess.getUserName();
		Constant.useId = userId;
		httpURLConnection.disconnect();
		
		return success;
	}

	private void processPassword() {
		userInfoPref = getSharedPreferences(USERINFO, MODE_PRIVATE);
		String account = editTextAccount.getText().toString();
		String password = editTextPassword.getText().toString();
		if (checkBox.isChecked()) {
			setInformationIntoPreferences(userInfoPref, account, password);
		} else {
			setInformationIntoPreferences(userInfoPref, account, null);

		}
	}

	private String getLastAccout() {
		lastAccountPref = getSharedPreferences(LASTACCOUNT, MODE_PRIVATE);
		String account = lastAccountPref.getString(accountKey, null);
		return account;
	}

	private String getLastPassword() {
		userInfoPref = getSharedPreferences(USERINFO, MODE_PRIVATE);
		String account = editTextAccount.getText().toString();
		return getInformationFromPreferences(userInfoPref, account);
	}

	private void setPresentAccount() {
		SharedPreferences.Editor editor = lastAccountPref.edit();
		editor.putString(accountKey, String.valueOf(editTextAccount.getText()
				.toString()));
		editor.commit();
	}

	protected void setInformationIntoPreferences(SharedPreferences sP,
			String key, String value) {
		SharedPreferences.Editor editor = sP.edit();
		editor.putString(key, value);
		editor.commit();
	}

	protected String getInformationFromPreferences(SharedPreferences sP,
			String key) {
		String value = sP.getString(key, null);
		return value;
	}

}
