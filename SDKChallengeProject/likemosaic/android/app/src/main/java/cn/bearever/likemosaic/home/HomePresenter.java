package cn.bearever.likemosaic.home;

import android.content.Context;

import cn.bearever.likemosaic.UidUtil;
import cn.bearever.likemosaic.bean.BaseResultBean;
import cn.bearever.likemosaic.bean.MatchResultBean;
import cn.bearever.mingbase.BaseCallback;
import cn.bearever.mingbase.app.mvp.BasePresenterIml;
import cn.bearever.mingbase.app.util.ToastUtil;

/**
 * @author luoming
 * @date 2020/4/12
 */
public class HomePresenter extends BasePresenterIml<HomeContact.View, HomeContact.Model> implements HomeContact.Presenter {
    public HomePresenter(HomeContact.View mView, Context context) {
        super(mView, context);
    }

    @Override
    public void initModel() {
        mModel = new HomeModel(getContext());
    }

    @Override
    public void requestMatch() {
        mModel.postMatch(UidUtil.getUid(getContext()), new BaseCallback<BaseResultBean>() {
            @Override
            public void suc(BaseResultBean data) {
                getMatchState();
            }

            @Override
            public void fail(String msg, int code) {
                super.fail(msg, code);
                ToastUtil.show(msg);
            }
        });
    }

    @Override
    public void stopMatch() {
        mModel.stopMatch();
    }

    @Override
    public void getMatchState() {
        mModel.getMatchState(UidUtil.getUid(getContext()), new BaseCallback<MatchResultBean>() {
            @Override
            public void suc(MatchResultBean data) {
                if (getView() == null) {
                    return;
                }

                getView().matchSucceed(data);
            }

            @Override
            public void fail(String msg, int code) {
                if (getView() == null) {
                    return;
                }

                getView().matchFailed(msg);
            }
        });
    }
}
