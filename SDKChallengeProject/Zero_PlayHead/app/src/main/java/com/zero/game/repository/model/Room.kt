package com.zero.game.repository.model

import java.io.Serializable

data class Room(
    val id: String,
    val token: String,
    val status: Int,
    val channel: String,
    val uid: Int,
    val type: String,
    val name: String,
    val created_at: String
) : Serializable

data class RoomCreate(val type: String, val name: String)

data class RoomCreateResult(val code: Int, val msg: String, val data: Room?)

data class RoomJoin(val room_id: String)

data class RoomLeave(val room_id: String)

data class RoomJoinResult(val code: Int, val msg: String,val data:Room?)

data class RoomLeaveResult(val code: Int, val msg: String)