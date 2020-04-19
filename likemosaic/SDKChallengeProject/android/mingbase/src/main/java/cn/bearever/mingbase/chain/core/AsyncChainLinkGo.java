package cn.bearever.mingbase.chain.core;

import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * 异步链的包装类，用来控制调用者可以访问的方法，主要目的是执行了`error*`方法之后就只能执行`go*`方法。
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/8/5
 **/
public class AsyncChainLinkGo {
    private AsyncChainLink link;

    /**
     * 构造函数
     *
     * @param link {@link AsyncChainLink}
     */
    public AsyncChainLinkGo(AsyncChainLink link) {
        this.link = link;
    }

    /**
     * 开始执行
     */
    public void go(AppCompatActivity activity) {
        link.go(activity);
    }

    /**
     * 开始执行
     *
     * @param fragment 绑定生命周期的fragment
     */
    public void go(Fragment fragment) {
        link.go(fragment);
    }

    /**
     * 开始执行
     *
     * @param view 绑定生命周期的view
     */
    public void go(View view) {
        link.go(view);
    }

    /**
     * 开始执行
     *
     * @param context 绑定生命周期的context
     */
    public void go(Context context) {
        link.go(context);
    }
}
