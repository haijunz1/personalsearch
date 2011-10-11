package com.android.wheretogo.sharepreferences;

import android.content.SharedPreferences;
import android.content.ContextWrapper;


public class SaveAndGetInformationPreferences {
	
	public void saveInformatinIntoPreferences(SharedPreferences sP,String key,String keyValue){
		//����userId��preferences��
		SharedPreferences.Editor editor = sP.edit();
		editor.putString(key,keyValue);
		editor.commit();
	}
	public String getInformationFromPreferences(SharedPreferences sP,String key){
		String value = sP.getString(key, null);
		return value;
	}

}
