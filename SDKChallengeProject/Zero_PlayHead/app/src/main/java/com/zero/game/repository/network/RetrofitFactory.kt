package com.zero.game.repository.network

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.zero.game.utils.AppConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    private val serviceMap = hashMapOf<String, Retrofit>()
    private var playHeadApi : PlayHeadApi? = null

    fun getPlayHeadApi():PlayHeadApi{
        playHeadApi?.let {
            return it
        }?: kotlin.run {
            val api = getRetrofitService(AppConfig.TEST_URL)
                .create(PlayHeadApi::class.java)
            playHeadApi = api
            return api
        }
    }

    fun getRetrofitService(baseUrl: String): Retrofit {
        serviceMap[baseUrl]?.let {
            return it
        }
        val client = OkHttpClient.Builder()
            .connectTimeout(AppConfig.NET_TIME_OUT_MS, TimeUnit.MILLISECONDS)
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val builder: Request.Builder = chain.request().newBuilder()
                    if (AppConfig.userToken.isNotEmpty()) {
                        builder.addHeader("SessionKey",AppConfig.userToken)
                    }
                    val request = builder.build()
                    return chain.proceed(request)
                }
            })
            .addInterceptor(LoggingInterceptor.Builder().setLevel(Level.BASIC).build())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        serviceMap[baseUrl] = retrofit
        return retrofit
    }

}