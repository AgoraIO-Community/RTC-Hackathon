package cn.bearever.mingbase.app.mvp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * activity的基类
 *
 * @author luoming
 * @date 2020/4/12
 */
public abstract class BaseActivity<P extends IBasePresenter> extends AppCompatActivity
        implements IBaseView {
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initData(savedInstanceState);
        initView();
        initPresenter();
    }


    /**
     * 获取布局的资源id
     *
     * @return 布局的资源id
     */
    public abstract int getLayoutId();

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    /**
     * 初始化UI
     */
    protected abstract void initView();

    /**
     * 初始化Presenter
     */
    protected abstract void initPresenter();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDetach();
        }
    }
}
