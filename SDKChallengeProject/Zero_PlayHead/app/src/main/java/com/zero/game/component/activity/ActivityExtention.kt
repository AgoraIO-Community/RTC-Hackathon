package com.zero.game.component.activity

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import com.ldoublem.loadingviewlib.view.LVCircularRing
import com.zero.game.R


/*************************** full screen / keep awake *********************************/

var wakeLock: WakeLock? = null

fun Activity.setScreenAlwaysWaked(alwaysWaked: Boolean) {
    if (alwaysWaked) {
        val powerManager = application.getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(
            PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ON_AFTER_RELEASE,
            this.toString()
        )
        wakeLock?.acquire(10 * 60 * 1000L /*10 minutes*/)
    } else {
        if (wakeLock != null && wakeLock?.isHeld == true) {
            wakeLock?.release()
        }
        wakeLock = null
    }
}

fun Activity.showLoading(): Dialog {
    val loadingDialog = Dialog(this, R.style.loading_dialog)
    loadingDialog.setContentView(
        LayoutInflater.from(this).inflate(R.layout.view_loading, null)
    )
    val lp: WindowManager.LayoutParams = loadingDialog.window!!.attributes
    lp.width = WindowManager.LayoutParams.MATCH_PARENT
    lp.height = WindowManager.LayoutParams.MATCH_PARENT
    loadingDialog.window!!.attributes = lp
    loadingDialog.setCancelable(true)
    loadingDialog.setCanceledOnTouchOutside(true)
    loadingDialog.show()
    loadingDialog.findViewById<LVCircularRing>(R.id.ring).startAnim()
    return loadingDialog
}

fun Activity.toast(string:Int){
    Toast.makeText(this,getString(string),Toast.LENGTH_SHORT).show()
}

fun Activity.toast(string:String){
    Toast.makeText(this,string,Toast.LENGTH_SHORT).show()
}