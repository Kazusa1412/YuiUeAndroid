package com.elouyi.yuiue.activity

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.elouyi.yuiue.R
import com.elouyi.yuiue.databinding.ActivityChatRoomBinding
import com.elouyi.yuiue.jetpack.viewModel.ChatRoomViewModel
import com.elouyi.yuiue.util.YwMsg
import com.elouyi.yuiue.yw.YwObject
import com.elouyi.yuiue.yw.component.YuiChatService
import com.elouyi.yuiue.yw.pojo.ElyMsg
import com.google.gson.Gson

class ChatRoomActivity : ElyActivity() {

    private lateinit var binding: ActivityChatRoomBinding
    private val viewModel: ChatRoomViewModel by viewModels()

    companion object{
        const val ACTION_YUI_SERVICE = "action.Yui.Service"
    }

    lateinit var receiver: YuiMsgReceiver

    lateinit var yuiBinder: YuiChatService.YuiChatBinder
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            yuiBinder = service as YuiChatService.YuiChatBinder
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = resources.getString(R.string.chatRoom)
        val intent = Intent(this, YuiChatService::class.java)
        bindService(intent,connection, BIND_AUTO_CREATE)
        initView()
        val filter = IntentFilter()
        filter.addAction(ACTION_YUI_SERVICE)
        receiver = YuiMsgReceiver()
        registerReceiver(receiver,filter)
    }

    private fun initView(){
        binding.recChatRoom.layoutManager = LinearLayoutManager(this)
        binding.recChatRoom.adapter = viewModel.getAdapter()
        binding.btChatRoomSend.setOnClickListener {
            val msg = binding.etChatRoom.text.toString()
            if (msg.isEmpty()) return@setOnClickListener
            binding.etChatRoom.setText("")
            sendMsg(msg)
        }

        viewModel.msgList.observe(this){
            Log.i("observe","list 变化了")
            val position = viewModel.getMsgList()?.size?.minus(1)
            viewModel.getAdapter()?.notifyItemInserted(position!!)
            binding.recChatRoom.scrollToPosition(position!!)
        }
    }

    private fun sendMsg(msg: String){
        yuiBinder.sendMsg(msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
        unregisterReceiver(receiver)
        stopService(Intent(this,YuiChatService::class.java))
    }

    inner class YuiMsgReceiver : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val msg = Gson().fromJson(intent?.extras?.getString("newMsg"),ElyMsg::class.java)
            msg?.let{
                viewModel.getMsgList()?.add(
                    YwMsg("${msg.from} : ${msg.content}",
                        if (msg.from == YwObject.loginUser.user_name) YwMsg.TYPE_SENT else YwMsg.TYPE_RECEIVED))

                val position = viewModel.getMsgList()?.size?.minus(1)
                viewModel.getAdapter()?.notifyItemInserted(position!!)
                binding.recChatRoom.scrollToPosition(position!!)
                Log.v("收到广播",msg.content)
            }
        }
    }
}