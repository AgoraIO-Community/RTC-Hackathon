package mobi.accessible.show.util;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;


/**
 * @aruth wlk
 * Created by 98283 on 2019/6/14.
 */
public class DialogUtils {
    /**
     * 是否退出直播间弹框
     * @param context
     * @param listener
     */
    public static void showExitDialog(Context context, final OnPositivieClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("退到后台", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(listener != null){
                    listener.onCLick(false);
                }
            }
        });
        builder.setNegativeButton("退出房间", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(listener != null){
                    listener.onCLick(true);
                }
            }
        });

        builder.setTitle("是否离开直播间");
        builder.setMessage("离开不收听请选退出房间，离开但是继续收听请选退到后台");
        builder.show();
    }
    /**
     * 是否退出直播间弹框
     * @param context
     * @param listener
     */
    public static void showApplyForDialog(Context context, String name, final OnPositivieClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("同意连麦", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(listener != null){
                    listener.onCLick(true);
                }
            }
        });
        builder.setNegativeButton("拒绝连麦", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(listener != null){
                    listener.onCLick(false);
                }
            }
        });

        builder.setTitle("连麦申请");
        builder.setMessage("是否同意 "+name+" 的连麦申请？");
        builder.show();
    }
    /**
     * 是否强制退出直播间弹框
     * @param context
     * @param listener
     */
    public static void showGameOverDialog(Context context, String name, final OnPositivieClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("强制下线", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(listener != null){
                    listener.onCLick(true);
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(listener != null){
                    listener.onCLick(false);
                }
            }
        });

        builder.setTitle("强制退出直播间");
        builder.setMessage("是否强制 "+name+" 退出直播间？");
        builder.show();
    }
     public interface OnPositivieClickListener{
         /**
          * @param consent
          */
        void onCLick(boolean consent);
     }


}
