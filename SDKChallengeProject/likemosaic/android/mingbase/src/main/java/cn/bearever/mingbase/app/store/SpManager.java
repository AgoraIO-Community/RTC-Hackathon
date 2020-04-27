package cn.bearever.mingbase.app.store;

import android.content.Context;
import android.content.SharedPreferences;

import cn.bearever.mingbase.app.BaseApplication;

/**
 * @author luoming
 * @date 2020/4/16
 */
public class SpManager {
    private static volatile SpManager instance;
    private SharedPreferences mSp;
    private SharedPreferences.Editor mEditor;

    public static SpManager getInstance() {
        if (instance == null) {
            synchronized (SpManager.class) {
                if (instance == null) {
                    instance = new SpManager();
                }
            }
        }
        return instance;
    }

    private SpManager() {
        mSp = BaseApplication.getApplication().getSharedPreferences(
                BaseApplication.getApplication().getPackageName(), Context.MODE_PRIVATE);
        mEditor = mSp.edit();
    }

    public void putInt(String key, int data) {
        mEditor.putInt(key, data);
        mEditor.apply();
    }

    public int getInt(String key) {
        return mSp.getInt(key, 0);
    }

    public void putString(String key, String data) {
        mEditor.putString(key, data);
        mEditor.apply();
    }

    public String getString(String key) {
        return mSp.getString(key, "");
    }

}
