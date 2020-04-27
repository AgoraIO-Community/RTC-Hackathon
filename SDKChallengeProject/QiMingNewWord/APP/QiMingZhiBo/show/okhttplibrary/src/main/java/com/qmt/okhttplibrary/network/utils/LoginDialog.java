package com.qmt.okhttplibrary.network.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qmt.okhttplibrary.R;


/**
 * 旋转弹框
 *
 * @author Luo Fuqing
 * @date 2018/03/13
 */
public class LoginDialog {
    private static LoginDialog mInstance;
    private static AlertDialog dlg;


    private synchronized static LoginDialog getInstance() {
        if (mInstance == null) {
            mInstance = new LoginDialog();
        }
        return mInstance;
    }


    /**
     * 弹框
     * @param context 上下文
     * @param s 弹出框的文字
     * @param canClose 是否可以关闭弹框
     */
    public static void showDialog(Context context, String s, boolean canClose) {
        getInstance().show(context, s,canClose);

    }

    /**
     * 关闭弹框
     */
    public static void dismissDialog() {
        getInstance().dismiss();
    }


    public static boolean dialogIsShowIng(){
        return dlg!=null&&dlg.isShowing();
    }


    /**
     * 加载弹出框
     * @param context 上下文
     * @param string  显示加载内容
     */
    private void show(Context context, String string, boolean canClose) {
        if (dlg==null) {
            dlg = new AlertDialog.Builder(context, R.style.myDialogStyle_transparent).create();
            dlg.show();
            Window window = dlg.getWindow();
            if (window != null) {
                window.setContentView(R.layout.dialog_login);
                LinearLayout dialogView = (LinearLayout) window.findViewById(R.id.dialog_view);
                ImageView iv = (ImageView) window.findViewById(R.id.iv_logo);
                RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(2000);
                animation.setRepeatCount(Animation.INFINITE);
                animation.start();
                iv.setAnimation(animation);
                TextView tv = (TextView) window.findViewById(R.id.tv_title);
                if (!TextUtils.isEmpty(string)) {
                    tv.setText(string);
                }

                if (canClose) {
                    dialogView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dismiss();
                        }
                    });
                }
            }
            dlg.setCancelable(false);
            dlg.setCanceledOnTouchOutside(false);
        }else {
            dlg.show();
        }

    }

    /**
     * 关闭加载框
     */
    private void dismiss() {
        if (dlg != null && dlg.isShowing()) {
            dlg.dismiss();
            dlg =null;
        }
    }

}
