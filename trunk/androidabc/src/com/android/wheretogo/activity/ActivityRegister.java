package com.android.wheretogo.activity;

import java.io.IOException;
import java.net.HttpURLConnection;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;

import com.android.wheretogo.constant.Constant;
import com.android.wheretogo.setconnection.SetHttpURLConnection;
import com.w2g.objects.User;
import com.w2g.operateObject.OperateObject;

public class ActivityRegister extends Activity {
	private EditText textUserName = null;
	private EditText textPassword = null;
	private EditText textPasswordAgain = null;
	private Button buttonRegister = null;
	private Button buttonBackLogin = null;
	private OnClickListener clickListenerRegister = null;
	private OnClickListener clickListenerBackLogin = null;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		textUserName = (EditText)findViewById(R.id.editText1);
		textPassword = (EditText)findViewById(R.id.editText2);
		textPasswordAgain = (EditText)findViewById(R.id.editText3);
		buttonRegister = (Button)findViewById(R.id.button1);
		buttonBackLogin = (Button)findViewById(R.id.button2);
		
		clickListenerRegister = new OnClickListener(){
			@Override
			public void onClick(View v) {
//				String uri = "http://203.91.121.3:8085/ServerW2G";
				String uri = Constant.uri;
				String userName = textUserName.getText().toString();
				String password = textPassword.getText().toString();
				String passwordAgain = textPasswordAgain.getText().toString();
				if(userName.equals("")){
					new AlertDialog.Builder(ActivityRegister.this)
					.setTitle("错误")
					.setMessage("用户名不能为空")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}).show();
				}
				else if(!password.equals(passwordAgain)){
					new AlertDialog.Builder(ActivityRegister.this)
					.setTitle("错误")
					.setMessage("密码不一致")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}).show();
				}
				else{
					User user = new User();
					user.setUserId(-1);
					user.setUserName(userName);
					user.setPassword(password);
					
					//set conn
					SetHttpURLConnection setHUC = new SetHttpURLConnection();
					HttpURLConnection httpURLConnection = setHUC.setHttpURLConnection(uri+"/register.do");
					
					OperateObject operateObject = new OperateObject();
					try {
						operateObject.writeObjectIntoOutStream(user, httpURLConnection.getOutputStream());
						Object object = operateObject.readObjectFromInputStream(httpURLConnection.getInputStream());
						User newUser = (User)object;
						int newUserId = newUser.getUserId();
						setTitle("请记住你的ID：" + newUserId);
						
						new AlertDialog.Builder(ActivityRegister.this)
						.setTitle("提示")
						.setMessage("请记住你的ID：" + newUserId)
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
							}
						}).show();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}//end else
				
			}
		};//end clickListenerRegister
		clickListenerBackLogin = new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActivityRegister.this,ActivityLogin.class);
				startActivity(intent);
			}
		};
		buttonRegister.setOnClickListener(clickListenerRegister);
		buttonBackLogin.setOnClickListener(clickListenerBackLogin);
	}
}
