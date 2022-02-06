package com.example.messenger.models

import android.os.Parcelable
import android.widget.ImageView
import android.widget.TextView
import com.example.messenger.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val uid: String?,
    val username: String?,
    val profileImageUrl: String
) : Parcelable {
    constructor() : this("", "", "")
}

class UserItem(val user: User) : Item<GroupieViewHolder>() {

    override fun getLayout() = R.layout.item_view_new_message

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.user_username_tv).text = user.username
        Picasso.get().load(user.profileImageUrl)
            .into(viewHolder.itemView.findViewById(R.id.user_profile_iv) as ImageView)
    }
}

class SendMessageItem(val text: String) : Item<GroupieViewHolder>() {
    override fun getLayout() = R.layout.chat_to_row

    override fun bind(p0: GroupieViewHolder, p1: Int) {
        p0.itemView.findViewById<TextView>(R.id.send_user_tv).text = text
    }
}

class ReceiveMessageItem(val text: String) : Item<GroupieViewHolder>() {
    override fun getLayout() = R.layout.chat_from_row

    override fun bind(p0: GroupieViewHolder, p1: Int) {
        p0.itemView.findViewById<TextView>(R.id.receive_user_tv).text = text
    }
}

class ChatMessage(
    val id: String,
    val text: String,
    val fromId: String,
    val toId: String,
    val timeStamp: Long
) {
    constructor() : this("", "", "", "", -1)

}
