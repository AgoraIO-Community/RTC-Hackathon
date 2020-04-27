package cn.bearever.likemosaic.home

import android.content.Context
import cn.bearever.likemosaic.UidUtil
import cn.bearever.likemosaic.bean.BaseResultBean
import cn.bearever.likemosaic.bean.MatchResultBean
import cn.bearever.mingbase.BaseCallback
import cn.bearever.mingbase.app.mvp.BasePresenterIml
import cn.bearever.mingbase.app.util.ToastUtil

/**
 * @author luoming
 * @date 2020/4/12
 */
class HomePresenter(mView: HomeContact.View?, context: Context?) : BasePresenterIml<HomeContact.View?,
        HomeContact.Model?>(mView, context), HomeContact.Presenter {
    override fun initModel() {
        mModel = HomeModel(context)
    }

    override fun requestMatch() {
        mModel?.postMatch(UidUtil.getUid(context), object : BaseCallback<BaseResultBean?>() {
            override fun fail(msg: String, code: Int) {
                super.fail(msg, code)
                ToastUtil.show(msg)
            }

            override fun suc(data: BaseResultBean) {
                getMatchState()

            }
        })
    }

    override fun stopMatch() {
        mModel?.stopMatch()
    }

    override fun getMatchState() {
        if (context == null) {
            return
        }
        mModel?.getMatchState(UidUtil.getUid(context), object : BaseCallback<MatchResultBean?>() {
            override fun suc(data: MatchResultBean) {
                if (view == null) {
                    return
                }
                view?.matchSucceed(data)
            }

            override fun fail(msg: String, code: Int) {
                if (view == null) {
                    return
                }
                view?.matchFailed(msg)
            }
        })
    }
}