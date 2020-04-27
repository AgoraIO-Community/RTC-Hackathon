package mobi.accessible.show;

import android.app.Application;

import mobi.accessible.show.manager.RtcManager;
import mobi.accessible.show.manager.RtmManager;

public class ChatRoomApplication extends Application {

    public static Application instance;
    public static String baseUrl = "https://api.agora.io/dev";
    public static String BaseUrl = "https://api.agora.io/v1";

    public ChatRoomApplication() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RtcManager.instance(this).init();
        RtmManager.instance(this).init();
    }

}
