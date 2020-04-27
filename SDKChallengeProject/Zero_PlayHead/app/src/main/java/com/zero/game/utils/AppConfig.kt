package com.zero.game.utils

import android.content.res.Resources
import com.zero.game.utils.sharepreference.SpDelegate

object AppConfig{

    const val appKey = ""

    const val NET_TIME_OUT_MS = 10000L

    const val TEST_URL = "https://playhead.echolab.cn"

    var userToken by SpDelegate<String>("userToken","")
    var userName by SpDelegate<String>("userName","")

    fun dp2px(dpValue: Float): Int {
        return (0.5f + dpValue * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun dp2px(dpValue: Int): Int {
        return (0.5f + dpValue * Resources.getSystem().displayMetrics.density).toInt()
    }

}