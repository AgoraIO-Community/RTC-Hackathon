package cn.bearever.likemosaic.home;

import android.content.Context;

import java.io.IOException;

import cn.bearever.likemosaic.Constant;
import cn.bearever.likemosaic.MosaiApplication;
import cn.bearever.likemosaic.bean.BaseResultBean;
import cn.bearever.likemosaic.bean.MatchResultBean;
import cn.bearever.mingbase.BaseCallback;
import cn.bearever.mingbase.chain.AsyncChain;
import cn.bearever.mingbase.chain.core.AsyncChainRunnable;
import cn.bearever.mingbase.chain.core.AsyncChainTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author luoming
 * @date 2020/4/12
 */
public class HomeModel implements HomeContact.Model {
    private Context context;
    private HomeService mService;

    public HomeModel(Context context) {
        this.context = context;
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.APP_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = mRetrofit.create(HomeService.class);
    }

    @Override
    public void postMatch(String uid, final BaseCallback callback) {
        Call<BaseResultBean> result = mService.postMatch(uid);
        result.enqueue(new Callback<BaseResultBean>() {
            @Override
            public void onResponse(Call<BaseResultBean> call, Response<BaseResultBean> response) {
                if (response.body() != null && callback != null) {
                    if (response.body().code == BaseResultBean.CODE_SUCCEED) {
                        callback.suc(response.body());
                    } else {
                        callback.fail(response.body().msg, response.body().code);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResultBean> call, Throwable t) {
                if (callback != null) {
                    callback.fail(t.getMessage(), BaseResultBean.CODE_FAILED);
                }
            }
        });
    }

    @Override
    public void stopMatch() {
        mGetStateCallback = null;
    }

    private volatile BaseCallback mGetStateCallback;

    @Override
    public void getMatchState(final String uid, BaseCallback<MatchResultBean> callback) {
        mGetStateCallback = callback;
        AsyncChain.withWork(new AsyncChainRunnable<Void, MatchResultBean>() {
            @Override
            public void run(AsyncChainTask<Void, MatchResultBean> task) throws Exception {
                MatchResultBean matchResultBean = null;

                for (int i = 0; i < 25; i++) {
                    Thread.sleep(1000);
                    matchResultBean = getMatchStateNet(uid, mGetStateCallback);
                    if (matchResultBean != null && matchResultBean.code == BaseResultBean.CODE_SUCCEED) {
                        break;
                    }
                    if (mGetStateCallback == null) {
                        task.onComplete();
                        return;
                    }
                }
                if (matchResultBean == null || matchResultBean.code == BaseResultBean.CODE_FAILED) {
                    matchResultBean = new MatchResultBean();
                    matchResultBean.code = BaseResultBean.CODE_FAILED;
                    matchResultBean.msg = "匹配失败，请稍后重试";
                }

                task.onNext(matchResultBean);
            }
        }).withMain(new AsyncChainRunnable<MatchResultBean, Void>() {
            @Override
            public void run(AsyncChainTask<MatchResultBean, Void> task) throws Exception {
                if (mGetStateCallback != null) {
                    if (task.getLastResult().code == BaseResultBean.CODE_SUCCEED) {
                        mGetStateCallback.suc(task.getLastResult());
                    } else {
                        mGetStateCallback.fail(task.getLastResult().msg, task.getLastResult().code);
                    }
                }
            }
        }).go(context);
    }

    private MatchResultBean getMatchStateNet(String uid, final BaseCallback<MatchResultBean> callback) throws IOException {
        Call<MatchResultBean> matchResultBeanCall = mService.getMatchState(uid);
        Response<MatchResultBean> response = matchResultBeanCall.execute();
        return response.body();
    }

    @Override
    public void onDetach() {
        mService = null;
        context = null;
    }

    public interface HomeService {
        @GET("match/post")
        Call<BaseResultBean> postMatch(@Query("uid") String uid);

        @GET("match/get")
        Call<MatchResultBean> getMatchState(@Query("uid") String uid);
    }
}
