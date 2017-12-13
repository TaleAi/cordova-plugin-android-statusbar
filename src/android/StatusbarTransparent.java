package com.liegouchina.cordova.plugin.statusbar;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import android.view.View;
import android.view.Window;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.WindowManager.LayoutParams;

public class StatusbarTransparent extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callback) throws JSONException {
        // grab the correct methods
        if (action.equalsIgnoreCase("enable")) {
            if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
                cordova.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        setStatusBarUpperAPI21();
                    }
                });
                callback.success();
            } else if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
                cordova.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        setStatusBarUpperAPI19();
                    }
                });
                callback.success();
            } else {
                callback.error("not supported");
            }
            return true;
        } else if (action.equalsIgnoreCase("disable")) {
            if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
                cordova.getActivity().runOnUiThread(new Runnable() {
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
        Window window = cordova.getActivity().getWindow();
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    // 4.4 - 5.0版本
    private void setStatusBarUpperAPI19() {
        Window window = cordova.getActivity().getWindow();
        window.addFlags(LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
}