package com.zero.game.component.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.zero.game.R
import com.zero.game.repository.model.UserId
import com.zero.game.repository.network.RetrofitFactory
import com.zero.game.utils.AppConfig
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.runBlocking
import java.lang.Exception


class LoginActivity : AppCompatActivity() {

    private val REQUESTED_PERMISSIONS = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var hasPermission = true
        for (permission in REQUESTED_PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PERMISSION_GRANTED) {
                hasPermission = false
            }
        }
        if (hasPermission && AppConfig.userToken.isNotEmpty()) {
            launch()
            return
        }

        ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, 100)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            val name = ed_user_name.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(this, getString(R.string.input_name_tip), Toast.LENGTH_SHORT).show()
            } else {
                runBlocking {
                    try {
                        val results = RetrofitFactory.getPlayHeadApi()
                            .signin(UserId(name, name))
                        if (results.data.session_key.isNotEmpty()) {
                            AppConfig.userToken = results.data.session_key
                            AppConfig.userName = name
                            launch()
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                getString(R.string.network_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.network_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (permissions.size != grantResults.size) {
                finish()
            } else {
                if (AppConfig.userToken.isNotEmpty()) {
                    launch()
                }
            }
        }
    }


    private fun launch() {
        val intent = Intent(this, ChoosePlayerActivity::class.java)
        startActivity(intent)
        finish()
    }


}