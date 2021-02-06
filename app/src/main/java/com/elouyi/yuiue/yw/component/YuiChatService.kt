package com.elouyi.yuiue.yw.component

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.elouyi.yuiue.activity.ChatRoomActivity
import com.elouyi.yuiue.yw.extension.showToast
import kotlinx.coroutines.*
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetSocketAddress
import java.net.Socket

class YuiChatService : Service() {

    lateinit var socket: Socket
    lateinit var dos: DataOutputStream
    lateinit var dis: DataInputStream
    private val mBinder = YuiChatBinder()
    private val job = Job()
    private val scope = CoroutineScope(job)


    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("YuiChatService","Yui服务启动")
        init()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("YuiChatService","Yui 服务销毁")
    }

    private fun init(){
        scope.launch {
            launch {
                try {
                    withContext(Dispatchers.IO){
                        socket = Socket()
                        socket.connect(InetSocketAddress("120.26.174.247",6666),3000)
                        dos = DataOutputStream(socket.getOutputStream())
                        dis = DataInputStream(socket.getInputStream())
                    }
                } catch (e: Exception) {
                    Log.e("YuiService","创建socket失败")
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        "连接到 Yui 服务器失败".showToast(Toast.LENGTH_LONG)
                    }
                }
            }.join()
            launch {
                receive()
            }
        }
    }

    fun check(){
        if (!this::socket.isInitialized) return
        if (!socket.isConnected){
            Log.i("YuiService","正在重连")
            scope.launch {
                try {
                    withContext(Dispatchers.IO){
                        socket = Socket()
                        socket.connect(InetSocketAddress("120.26.174.247",6666),3000)
                        dos = DataOutputStream(socket.getOutputStream())
                        dis = DataInputStream(socket.getInputStream())
                    }
                } catch (e: Exception) {
                    Log.e("YuiService","创建socket失败")
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        "连接到 Yui 服务器失败".showToast(Toast.LENGTH_LONG)
                    }
                }
            }
        }
    }

    private suspend fun receive(){
        withContext(Dispatchers.IO){
            while (true){
                try {
                    val msg = dis.readUTF()
                    val intent = Intent()
                    intent.action = ChatRoomActivity.ACTION_YUI_SERVICE
                    intent.putExtra("newMsg",msg)
                    sendBroadcast(intent)
                }catch (e: Exception){
                    Log.e("YuiService","接收消息错误")
                    e.printStackTrace()
                    check()
                }
            }
        }
    }

    inner class YuiChatBinder : Binder(){
        private val that = this@YuiChatService
        fun sendMsg(msg: String){
            if (!that::socket.isInitialized){
                "正在连接到服务器".showToast()
                return
            }
            if (!that.socket.isConnected){
                "正在连接到服务器".showToast()
                return
            }
            that.scope.launch {
                withContext(Dispatchers.IO){
                    try {
                        that.dos.writeUTF(msg)
                        that.dos.flush()
                    }catch (e: Exception){
                        Log.e("YuiChatService","发送消息失败")
                        e.printStackTrace()
                        withContext(Dispatchers.Main){
                            "发送消息失败".showToast()
                        }
                        check()
                    }
                }
            }
        }
    }
}