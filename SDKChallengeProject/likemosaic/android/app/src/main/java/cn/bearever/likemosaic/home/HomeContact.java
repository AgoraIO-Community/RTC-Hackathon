package cn.bearever.likemosaic.home;

import javax.security.auth.callback.Callback;

import cn.bearever.likemosaic.bean.MatchResultBean;
import cn.bearever.mingbase.BaseCallback;
import cn.bearever.mingbase.app.mvp.IBaseModel;
import cn.bearever.mingbase.app.mvp.IBasePresenter;
import cn.bearever.mingbase.app.mvp.IBaseView;

/**
 * @author luoming
 * @date 2020/4/12
 */
public class HomeContact {
    public interface View extends IBaseView {
        /**
         * 开始匹配
         */
        void startMatch();

        /**
         * 停止匹配
         */
        void stopMatch();

        /**
         * 匹配成功
         *
         * @param matchResultBean 匹配成功的数据
         */
        void matchSucceed(MatchResultBean matchResultBean);

        /**
         * 匹配失败
         * @param msg
         */
        void matchFailed(String msg);
    }

    public interface Model extends IBaseModel {
        /**
         * 提交匹配请求
         *
         * @param uid
         * @param callback
         */
        void postMatch(String uid, BaseCallback callback);

        /**
         * 获取匹配状态
         *
         * @param uid
         * @param callback
         */
        void getMatchState(String uid, BaseCallback<MatchResultBean> callback);

        /**
         * 停止匹配
         */
        void stopMatch();
    }

    public interface Presenter extends IBasePresenter {
        /**
         * 提交匹配请求
         */
        void requestMatch();

        /**
         * 获取匹配状态
         */
        void getMatchState();

        /**
         * 结束匹配
         */
        void stopMatch();
    }
}
