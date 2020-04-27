package com.pzj.visualcall.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.pzj.visualcall.client.utils.KeyboardHelper;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登陆界面
 */
public class MainActivity extends AppCompatActivity {



    //标记输入法界面需要遮挡区域的高度
    private int bottomHeight;
    //输入法帮助类
    private KeyboardHelper keyboardHelper;


     @BindView(R.id.loginLayout)
    LinearLayout loginLayout;

    @BindView(R.id.bottomLayout)
    LinearLayout bottomLayout;


    @BindView(R.id.bt_login)
    AppCompatButton bt_login;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化 View绑定框架
        ButterKnife.bind(this);

        //沉浸式状态栏
        QMUIStatusBarHelper.translucent(this);
        //设置状态栏白色字体和图标
        QMUIStatusBarHelper.setStatusBarDarkMode(this);

        //解决键盘档住输入框和登录按钮
        keyboardHelper = new KeyboardHelper(this);
        keyboardHelper.onCreate();
        keyboardHelper.setOnKeyboardStatusChangeListener(onKeyBoardStatusChangeListener);
        bottomLayout.post(new Runnable() {
            @Override
            public void run() {
                bottomHeight = bottomLayout.getHeight();
            }
        });

    }

    @Override
    public Resources getResources() {
        return super.getResources();
    }


    //登陆
    @OnClick(R.id.bt_login)
    public void onLoginClick(){
        startActivity(new Intent("com.pzj.visualcall.client.home"));
        this.finish();
    }



    //调用隐藏系统默认的输入法
    public static void inputMethoHide(Context context, Activity activity) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }


    //监听输入法弹出关闭
    private KeyboardHelper.OnKeyboardStatusChangeListener onKeyBoardStatusChangeListener = new KeyboardHelper.OnKeyboardStatusChangeListener() {

        @Override
        public void onKeyboardPop(int keyboardHeight) {
            //输入法弹出

            if (bottomHeight > keyboardHeight) {
                bottomLayout.setVisibility(View.GONE);
            } else {
                int offset = bottomHeight - (keyboardHeight+getNavigationBarHeight());
                final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) loginLayout
                        .getLayoutParams();
                lp.topMargin = offset;
                loginLayout.setLayoutParams(lp);
            }

        }

        @Override
        public void onKeyboardClose(int keyboardHeight) {
            //输入法关闭
            if (View.VISIBLE != bottomLayout.getVisibility()) {
                bottomLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bottomLayout.setVisibility(View.VISIBLE);
                    }
                }, 300);
            }
            final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) loginLayout
                    .getLayoutParams();
            if (lp.topMargin != 0) {
                lp.topMargin = 0;
                loginLayout.setLayoutParams(lp);
            }

        }
    };


    //获取导航栏高度
    public int getNavigationBarHeight() {

        Resources resources = this.getResources();

        int resourceId=resources.getIdentifier("navigation_bar_height","dimen","android");

        int height = resources.getDimensionPixelSize(resourceId);

        Log.d("navigation bar>>>", "height:" + height);

        return height;

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            keyboardHelper.onDestroy();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
