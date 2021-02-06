package com.elouyi.yuiue.jetpack.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elouyi.yuiue.util.YwMsg
import com.elouyi.yuiue.util.YwMsgAdapter

class ChatRoomViewModel: ViewModel(){

    val msgList = MutableLiveData<ArrayList<YwMsg>>()
    private val adapter = MutableLiveData<YwMsgAdapter>()

    init {
        msgList.value = ArrayList()
        adapter.value = YwMsgAdapter(msgList.value!!)
    }

    fun getMsgList() = msgList.value
    fun getAdapter() = adapter.value
}