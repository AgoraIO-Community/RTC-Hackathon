package cn.bearever.mingbase.app.mvp;

import android.content.Context;

/**
 * @author luoming
 * @date 2020/4/12
 */
public interface IBasePresenter {
    /**
     * 销毁时的回调
     */
    void onDetach();
}
