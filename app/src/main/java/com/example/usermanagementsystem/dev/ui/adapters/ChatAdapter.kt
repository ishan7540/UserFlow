package com.example.usermanagementsystem.dev.core.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.usermanagementsystem.databinding.ItemChatReceiverBinding
import com.example.usermanagementsystem.databinding.ItemChatSenderBinding
import com.example.usermanagementsystem.dev.core.models.ChatMessage

class ChatAdapter(
    private val context: Context,
    private var listMessages: List<ChatMessage>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_TYPE_LEFT = 1001
    private val ITEM_TYPE_RIGHT = 1002

    inner class LeftMessageVH(val binding: ItemChatReceiverBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class RightMessageVH(val binding: ItemChatSenderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return if (listMessages[position].isSent == 1) ITEM_TYPE_RIGHT else ITEM_TYPE_LEFT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        return if (viewType == ITEM_TYPE_RIGHT) {
            RightMessageVH(ItemChatSenderBinding.inflate(inflater, parent, false))
        } else {
            LeftMessageVH(ItemChatReceiverBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = listMessages[position]
        if (holder is RightMessageVH) {
            holder.binding.messageText.text = message.message
        } else if (holder is LeftMessageVH) {
            (holder as LeftMessageVH).binding.messageText.text = message.message
        }
    }

    override fun getItemCount(): Int = listMessages.size

    fun setMessages(newMessages: List<ChatMessage>) {
        listMessages = newMessages.sortedBy { it.timestamp } // sort ascending
        notifyDataSetChanged()
    }
}
