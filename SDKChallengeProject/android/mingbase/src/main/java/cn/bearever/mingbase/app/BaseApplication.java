package cn.bearever.mingbase.app;

import android.app.Application;
import android.content.Context;

/**
 * @author luoming
 * @date 2020/4/16
 */
public class BaseApplication extends Application {
    private static Application app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    /**
     * 获取application
     *
     * @return
     */
    public static Application getApplication() {
        return app;
    }
}
