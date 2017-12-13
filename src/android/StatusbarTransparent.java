package com.liegouchina.cordova.plugin.statusbar;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.WindowManager.LayoutParams;

public class StatusbarTransparent extends CordovaPlugin {

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callback) throws JSONException {
		// grab the correct methods
		if(action.equalsIgnoreCase("enable")) {
			if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
				cordova.getActivity().runOnUiThread( new Runnable() {
					public void run() {
						setStatusBarUpperAPI21();
					}
				});
				callback.success();
			} else if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
				cordova.getActivity().runOnUiThread( new Runnable() {
					public void run() {
						setStatusBarUpperAPI19();
					}
				});
				callback.success();
			}else {
				callback.error("not supported");
			}
			return true;
		} else if(action.equalsIgnoreCase("disable")) {
			if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
				cordova.getActivity().runOnUiThread( new Runnable() {
					public void run() {
						cordova.getActivity().getWindow().clearFlags(LayoutParams.FLAG_TRANSLUCENT_STATUS);
					}
				});
				callback.success();
			} else {
				callback.error("not supported");
			}
			return true;
		} else {
			callback.error("Unknown Action: " + action);
			return false;
		}
	}

	// 5.0版本以上
	private void setStatusBarUpperAPI21() {
		//设置透明状态栏,这样才能让 ContentView 向上
		cordova.getActivity().getWindow().clearFlags(LayoutParams.FLAG_TRANSLUCENT_STATUS);

		//需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
		cordova.getActivity().getWindow().addFlags(LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
	}

	// 4.4 - 5.0版本
	private void setStatusBarUpperAPI19() {
		cordova.getActivity().getWindow().addFlags(LayoutParams.FLAG_TRANSLUCENT_STATUS);
	}
}