package com.zero.game.component.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zero.game.R
import com.zero.game.repository.model.Room

class RoomAdapter(val itemClickListener: ItemClickListener) : RecyclerView.Adapter<RoomAdapter.RoomHolder>() {

    private var list = arrayListOf<Room>()

    fun setRooms(rooms: List<Room>) {
        list.clear()
        list.addAll(rooms)
        notifyDataSetChanged()
    }

    class RoomHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val btnRoom = view.findViewById<ImageView>(R.id.btn_join)
        val tvNickname = view.findViewById<TextView>(R.id.item_nickname)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomHolder {
        return RoomHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RoomHolder, position: Int) {
        val room = list[position]
        holder.tvNickname.text = room.name
        holder.btnRoom.setOnClickListener {
            itemClickListener.onClick(room)
        }
    }

    interface ItemClickListener {
        fun onClick(room: Room)
    }
}