package com.zero.game.repository.network

import com.zero.game.repository.model.*
import retrofit2.http.*

interface PlayHeadApi {
    @Headers("Content-type:application/json")
    @POST("/user/signin")
    suspend fun signin(@Body id: UserId): User

    @GET("/room/list")
    suspend fun roomList():List<Room>

    @Headers("Content-type:application/json")
    @POST("/room/create")
    suspend fun createRoom(@Body type: RoomCreate): RoomCreateResult

    @Headers("Content-type:application/json")
    @POST("/room/join")
    suspend fun join(@Body id:RoomJoin):RoomJoinResult

    @Headers("Content-type:application/json")
    @POST("/room/leave")
    suspend fun leave(@Body id:RoomLeave):RoomLeaveResult


}