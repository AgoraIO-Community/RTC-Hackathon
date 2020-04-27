package com.zero.game.component.activity

import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zero.game.R
import com.zero.game.component.adapter.RoomAdapter
import com.zero.game.repository.model.*
import com.zero.game.repository.network.PlayHeadApi
import com.zero.game.repository.network.RetrofitFactory
import com.zero.game.utils.AppConfig
import kotlinx.android.synthetic.main.activity_choose_player.*
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import java.util.logging.Logger

class ChoosePlayerActivity : AppCompatActivity(), RoomAdapter.ItemClickListener {

    private var roomAdapter = RoomAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_player)

        room_list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        room_list.adapter = roomAdapter

        add_room.setOnClickListener {
            val dialog = showLoading()
            runBlocking {
                try {
                    val results =
                        RetrofitFactory.getPlayHeadApi()
                            .createRoom(RoomCreate("shuaiguo", AppConfig.userName))
                    if (results.data != null) {
                        VideoCallActivity.launch(
                            this@ChoosePlayerActivity
                            , results.data, Role.THROW_PLAYER
                        )
                    } else {
                        toast(R.string.network_error)
                    }
                    dialog.dismiss()
                } catch (e: Exception) {
                    e.printStackTrace()
                    toast(R.string.network_error)
                    dialog.dismiss()
                }
            }
        }

        refresh_btn.setOnClickListener {
            refresh()
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh() {
        runBlocking {
            try {
                val results = RetrofitFactory.getPlayHeadApi().roomList()
                roomAdapter.setRooms(results)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    override fun onClick(room: Room) {
//        VideoCallActivity.launch(this@ChoosePlayerActivity, room, Role.RECEIVE_PLAYER)

        val dialog = showLoading()
        runBlocking {
            try {
                val results = RetrofitFactory.getPlayHeadApi().join(RoomJoin(room.id))
                if (results.data != null) {
                    VideoCallActivity.launch(this@ChoosePlayerActivity, results.data, Role.RECEIVE_PLAYER)
                } else {
                    if (results.msg == "你已经在房间里了") {
                        toast(R.string.invalid_room)
                        RetrofitFactory.getPlayHeadApi().leave(RoomLeave(room.id))
                        refresh()
                    } else {
                        toast(results.msg)
                    }
                }
            } catch (e: Exception) {
                toast(R.string.network_error)
                e.printStackTrace()
            } finally {
                dialog.dismiss()
            }
        }
    }

}