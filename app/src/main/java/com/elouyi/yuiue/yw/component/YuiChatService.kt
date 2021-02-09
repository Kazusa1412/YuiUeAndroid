package com.elouyi.yuiue.yw.component

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.elouyi.yuiue.activity.ChatRoomActivity
import com.elouyi.yuiue.yw.YwObject
import com.elouyi.yuiue.yw.extension.showToast
import com.elouyi.yuiue.yw.pojo.ElyMsg
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.*
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.Socket

class YuiChatService : Service() {

    lateinit var socket: Socket
    lateinit var outputStream: OutputStream
    lateinit var inputStream: InputStream
    private val mBinder = YuiChatBinder()
    private val job = Job()
    private val scope = CoroutineScope(job)
    private var state = true

    // 120.26.174.247
    val url = "120.26.174.247"

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
        try {
            inputStream.close()
            outputStream.close()
            socket.close()
        }catch (e: Exception){

        }
        Log.i("YuiChatService","Yui 服务销毁")
    }

    private fun init(){
        scope.launch {
            launch {
                try {
                    withContext(Dispatchers.IO){
                        socket = Socket()
                        socket.connect(InetSocketAddress(url,6666),3000)
                        outputStream = socket.getOutputStream()
                        inputStream = socket.getInputStream()
                        outputStream.write(ElyMsg.changeNameMsg(YwObject.loginUser.user_name).toString().toByteArray())
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

    suspend fun check(){
        if (!this::socket.isInitialized) return
        if (!socket.isConnected){
            Log.i("YuiService","正在重连")
            scope.launch {
                try {
                    withContext(Dispatchers.IO){
                        socket = Socket()
                        // 120.26.174.247
                        socket.connect(InetSocketAddress(url,6666),3000)
                        inputStream = socket.getInputStream()
                        outputStream = socket.getOutputStream()
                        state = true
                        withContext(Dispatchers.Main){
                            "重连成功".showToast()
                        }
                        receive()
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
            while (state){
                try {
                    val buffer = ByteArray(4096)
                    val len = inputStream.read(buffer)
                    val msg = String(buffer,0,len)
                    Log.i("收到消息：",msg)
                    val msgP = Gson().fromJson(msg,ElyMsg::class.java)
                    Log.i("转换后的霞浦县",msgP.toString())
                    val intent = Intent()
                    intent.action = ChatRoomActivity.ACTION_YUI_SERVICE
                    intent.putExtra("newMsg",msg)
                    sendBroadcast(intent)
                }catch (e: JsonSyntaxException){
                    e.printStackTrace()
                }catch (e: Exception){
                    state = false
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
                        val msgP = ElyMsg.newMsg(msg).toString()
                        Log.v("尝试发送消息",msgP)
                        outputStream.write(msgP.toByteArray())
                        Log.v("发送成功","zz")
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