package cn.bearever.likemosaic;

import com.tencent.bugly.crashreport.CrashReport;

import cn.bearever.mingbase.app.BaseApplication;

/**
 * @author luoming
 * @date 2020/4/16
 */
public class MosaiApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "c95b940a85", false);
    }
}
