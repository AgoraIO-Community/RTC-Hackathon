package cn.bearever.mingbase.app.permission.info;


import android.Manifest;

/**
 * 权限枚举
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/9/2
 **/
public enum PermissionEnum {
    /**
     *
     */
    READ_CALENDAR(Manifest.permission.READ_CALENDAR, "查看日历权限"),
    WRITE_CALENDAR(Manifest.permission.WRITE_CALENDAR, "编辑日历权限"),
    CAMERA(Manifest.permission.CAMERA, "相机权限"),
    READ_CONTACTS(Manifest.permission.READ_CONTACTS, "读取联系人权限"),
    WRITE_CONTACTS(Manifest.permission.WRITE_CONTACTS, "编辑联系人权限"),
    GET_ACCOUNTS(Manifest.permission.GET_ACCOUNTS, "获取账号信息权限"),
    ACCESS_COARSE_LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION, "定位权限"),
    ACCESS_FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION, "定位权限"),
    RECORD_AUDIO(Manifest.permission.RECORD_AUDIO, "录音权限"),
    READ_PHONE_STATE(Manifest.permission.READ_PHONE_STATE, "获取手机状态权限"),
    CALL_PHONE(Manifest.permission.CALL_PHONE, "拨号权限"),
    READ_CALL_LOG(Manifest.permission.READ_CALL_LOG, "读取通话记录权限"),
    WRITE_CALL_LOG(Manifest.permission.WRITE_CALL_LOG, "编辑通话记录权限"),
    ADD_VOICEMAIL(Manifest.permission.ADD_VOICEMAIL, "添加语音邮件权限"),
    USE_SIP(Manifest.permission.USE_SIP, "SIP服务权限"),
    PROCESS_OUTGOING_CALLS(Manifest.permission.PROCESS_OUTGOING_CALLS, "查看呼叫号码权限"),
    BODY_SENSORS(Manifest.permission.BODY_SENSORS, "身体传感器权限"),
    SEND_SMS(Manifest.permission.SEND_SMS, "短信收发权限"),
    RECEIVE_SMS(Manifest.permission.RECEIVE_SMS, "短信收发权限"),
    READ_SMS(Manifest.permission.READ_SMS, "短信读取权限"),
    RECEIVE_WAP_PUSH(Manifest.permission.RECEIVE_WAP_PUSH, "接收彩信权限"),
    RECEIVE_MMS(Manifest.permission.RECEIVE_MMS, "接收彩信权限"),
    READ_EXTERNAL_STORAGE(Manifest.permission.READ_EXTERNAL_STORAGE, "存储读取权限"),
    WRITE_EXTERNAL_STORAGE(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储写入权限"),
    SYSTEM_ALERT_WINDOW(Manifest.permission.SYSTEM_ALERT_WINDOW, "显示悬浮窗权限"),
    WRITE_SETTINGS(Manifest.permission.WRITE_SETTINGS, "系统设置权限"),
    NORMAL("NORMAL", "必要权限");
    private String name;
    private String message;

    PermissionEnum(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public static PermissionEnum of(String permission) {
        switch (permission) {
            case Manifest.permission.READ_CALENDAR:
                return READ_CALENDAR;
            case Manifest.permission.WRITE_CALENDAR:
                return WRITE_CALENDAR;
            case Manifest.permission.CAMERA:
                return CAMERA;
            case Manifest.permission.READ_CONTACTS:
                return READ_CONTACTS;
            case Manifest.permission.WRITE_CONTACTS:
                return WRITE_CONTACTS;
            case Manifest.permission.GET_ACCOUNTS:
                return GET_ACCOUNTS;
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                return ACCESS_COARSE_LOCATION;
            case Manifest.permission.ACCESS_FINE_LOCATION:
                return ACCESS_FINE_LOCATION;
            case Manifest.permission.RECORD_AUDIO:
                return RECORD_AUDIO;
            case Manifest.permission.READ_PHONE_STATE:
                return READ_PHONE_STATE;
            case Manifest.permission.CALL_PHONE:
                return CALL_PHONE;
            case Manifest.permission.READ_CALL_LOG:
                return READ_CALL_LOG;
            case Manifest.permission.WRITE_CALL_LOG:
                return WRITE_CALL_LOG;
            case Manifest.permission.ADD_VOICEMAIL:
                return ADD_VOICEMAIL;
            case Manifest.permission.USE_SIP:
                return USE_SIP;
            case Manifest.permission.PROCESS_OUTGOING_CALLS:
                return PROCESS_OUTGOING_CALLS;
            case Manifest.permission.BODY_SENSORS:
                return BODY_SENSORS;
            case Manifest.permission.SEND_SMS:
                return SEND_SMS;
            case Manifest.permission.RECEIVE_SMS:
                return RECEIVE_SMS;
            case Manifest.permission.READ_SMS:
                return READ_SMS;
            case Manifest.permission.RECEIVE_WAP_PUSH:
                return RECEIVE_WAP_PUSH;
            case Manifest.permission.RECEIVE_MMS:
                return RECEIVE_MMS;
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                return READ_EXTERNAL_STORAGE;
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                return WRITE_EXTERNAL_STORAGE;
            case Manifest.permission.SYSTEM_ALERT_WINDOW:
                return SYSTEM_ALERT_WINDOW;
            case Manifest.permission.WRITE_SETTINGS:
                return WRITE_SETTINGS;
            default:
                return NORMAL;
        }
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
