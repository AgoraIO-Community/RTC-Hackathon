package cn.bearever.mingbase.app.mvp;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * @author luoming
 * @date 2020/4/12
 */
public abstract class BasePresenterIml<V extends IBaseView, M extends IBaseModel> implements IBasePresenter {
    private WeakReference<V> mViewRef;
    private WeakReference<Context> mContextRef;
    protected M mModel;

    public BasePresenterIml(V view, Context context) {
        this.mViewRef = new WeakReference<>(view);
        this.mContextRef = new WeakReference<>(context);
        initModel();
    }

    /**
     * 初始化model类，例如 mModel = new TestModel()
     */
    public abstract void initModel();

    public V getView() {
        if (isAttach()) {
            return mViewRef.get();
        } else {
            return null;
        }
    }

    public Context getContext() {
        if (isAttach()) {
            return mContextRef.get();
        }
        return null;
    }

    private boolean isAttach() {
        return null != mViewRef && null != mViewRef.get();
    }

    @Override
    public void onDetach() {
        mViewRef = null;
        mContextRef = null;
        if (mModel != null) {
            mModel.onDetach();
            mModel = null;
        }
    }
}
