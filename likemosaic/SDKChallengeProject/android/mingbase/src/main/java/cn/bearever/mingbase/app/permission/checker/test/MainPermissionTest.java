package cn.bearever.mingbase.app.permission.checker.test;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;

/**
 * 权限测试类
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/9/2
 **/
public class MainPermissionTest {
    private Context mContext;

    public MainPermissionTest(Context context) {
        this.mContext = context;
    }

    public boolean test(String permission) {
        try {
            switch (permission) {
                case Manifest.permission.READ_CALENDAR:
                    return checkReadCalendar(mContext);
                case Manifest.permission.WRITE_CALENDAR:
                    return checkWriteCalendar(mContext);
                case Manifest.permission.CAMERA:
                    return checkCamera(mContext);
                case Manifest.permission.READ_CONTACTS:
                    return checkReadContacts(mContext);
                case Manifest.permission.WRITE_CONTACTS:
                    return checkWriteContacts(mContext);
                case Manifest.permission.GET_ACCOUNTS:
                    return true;
                case Manifest.permission.ACCESS_COARSE_LOCATION:
                    return checkCoarseLocation(mContext);
                case Manifest.permission.ACCESS_FINE_LOCATION:
                    return checkFineLocation(mContext);
                case Manifest.permission.RECORD_AUDIO:
                    return checkRecordAudio(mContext);
                case Manifest.permission.READ_PHONE_STATE:
                    return checkReadPhoneState(mContext);
                case Manifest.permission.CALL_PHONE:
                    return true;
                case Manifest.permission.READ_CALL_LOG:
                    return checkReadCallLog(mContext);
                case Manifest.permission.WRITE_CALL_LOG:
                    return checkWriteCallLog(mContext);
                case Manifest.permission.ADD_VOICEMAIL:
                    return checkAddVoicemail(mContext);
                case Manifest.permission.USE_SIP:
                    return checkSip(mContext);
                case Manifest.permission.PROCESS_OUTGOING_CALLS:
                    return true;
                case Manifest.permission.BODY_SENSORS:
                    return checkSensors(mContext);
                case Manifest.permission.SEND_SMS:
                case Manifest.permission.RECEIVE_MMS:
                    return true;
                case Manifest.permission.READ_SMS:
                    return checkReadSms(mContext);
                case Manifest.permission.RECEIVE_WAP_PUSH:
                case Manifest.permission.RECEIVE_SMS:
                    return true;
                case Manifest.permission.READ_EXTERNAL_STORAGE:
                    return checkReadStorage();
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                    return checkWriteStorage();
                default:
                    break;
            }
        } catch (Throwable e) {
            return false;
        }
        return true;
    }

    private static boolean checkReadCalendar(Context context) throws Throwable {
        PermissionTest test = new CalendarReadTest(context);
        return test.test();
    }

    private static boolean checkWriteCalendar(Context context) throws Throwable {
        PermissionTest test = new CalendarWriteTest(context);
        return test.test();
    }

    private static boolean checkCamera(Context context) throws Throwable {
        PermissionTest test = new CameraTest(context);
        return test.test();
    }

    private static boolean checkReadContacts(Context context) throws Throwable {
        PermissionTest test = new ContactsReadTest(context);
        return test.test();
    }

    private static boolean checkWriteContacts(Context context) throws Throwable {
        ContentResolver resolver = context.getContentResolver();
        PermissionTest test = new ContactsWriteTest(resolver);
        return test.test();
    }

    private static boolean checkCoarseLocation(Context context) throws Throwable {
        PermissionTest test = new LocationCoarseTest(context);
        return test.test();
    }

    private static boolean checkFineLocation(Context context) throws Throwable {
        PermissionTest test = new LocationFineTest(context);
        return test.test();
    }

    private static boolean checkRecordAudio(Context context) throws Throwable {
        PermissionTest test = new RecordAudioTest(context);
        return test.test();
    }

    private static boolean checkReadPhoneState(Context context) throws Throwable {
        PermissionTest test = new PhoneStateReadTest(context);
        return test.test();
    }

    private static boolean checkReadCallLog(Context context) throws Throwable {
        PermissionTest test = new CallLogReadTest(context);
        return test.test();
    }

    private static boolean checkWriteCallLog(Context context) throws Throwable {
        PermissionTest test = new CallLogWriteTest(context);
        return test.test();
    }

    private static boolean checkAddVoicemail(Context context) throws Throwable {
        PermissionTest test = new AddVoicemailTest(context);
        return test.test();
    }

    private static boolean checkSip(Context context) throws Throwable {
        PermissionTest test = new SipTest(context);
        return test.test();
    }

    private static boolean checkSensors(Context context) throws Throwable {
        PermissionTest test = new SensorsTest(context);
        return test.test();
    }

    private static boolean checkReadSms(Context context) throws Throwable {
        PermissionTest test = new SmsReadTest(context);
        return test.test();
    }

    private static boolean checkReadStorage() throws Throwable {
        PermissionTest test = new StorageReadTest();
        return test.test();
    }

    private static boolean checkWriteStorage() throws Throwable {
        PermissionTest test = new StorageWriteTest();
        return test.test();
    }
}
