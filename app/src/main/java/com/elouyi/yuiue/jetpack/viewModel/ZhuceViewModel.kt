package com.elouyi.yuiue.jetpack.viewModel

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.elouyi.yuiue.ElyApplication
import com.elouyi.yuiue.retrofit.BaseResponse
import com.elouyi.yuiue.retrofit.LoginService
import com.elouyi.yuiue.retrofit.ServerCreater
import com.elouyi.yuiue.yw.YW_OK
import com.elouyi.yuiue.yw.component.Loginable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class ZhuceViewModel : Loginable() {

    val zhuceMessage = MutableLiveData<String>()
    val username = MutableLiveData<String>()

    fun setAccount(account: String){
        this.account.value = account
    }

    fun setPassword(password: String){
        this.password.value = password
    }

    fun setUsername(username: String){
        this.username.value = username
    }

    fun zhuce(){
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO){
                try {
                    ServerCreater.create<LoginService>().ueZhuce(
                        HashMap<String,Any?>().apply {
                            put("account",account.value)
                            put("password",password.value)
                            put("username",username.value)
                        }
                    ).execute().body()
                }catch (e: SocketTimeoutException){
                    Log.e("注册失败，网络超时",e.message.toString())
                    e.printStackTrace()
                    BaseResponse.error(-10,"注册网络超时")
                }
            }
            if (res != null){
                when(res.result_code){
                    0 -> {
                        // 注册成功
                        zhuceMessage.value = YW_OK
                        ElyApplication.context.getSharedPreferences("user", Context.MODE_PRIVATE).edit {
                            putString("account",account.value)
                            putString("password",password.value)
                        }
                    }
                    else -> {
                        zhuceMessage.value = res.message
                    }
                }
            }else {
                zhuceMessage.value = "res is null"
            }
        }
    }
}