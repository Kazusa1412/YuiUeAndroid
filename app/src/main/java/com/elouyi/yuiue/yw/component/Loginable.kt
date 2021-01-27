package com.elouyi.yuiue.yw.component

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elouyi.yuiue.ElyApplication
import com.elouyi.yuiue.retrofit.BaseResponse
import com.elouyi.yuiue.retrofit.LoginService
import com.elouyi.yuiue.retrofit.ServerCreater
import com.elouyi.yuiue.yw.LoginUser
import com.elouyi.yuiue.yw.YW_OK
import com.elouyi.yuiue.yw.YwObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

abstract class Loginable : ViewModel(){
    val account = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val loginMessage = MutableLiveData<String>()

    open fun login(){
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO){
                try {
                    ServerCreater.create<LoginService>().ueLogin(account.value,password.value).execute().body()
                } catch (e: SocketTimeoutException) {
                    Log.e("登陆失败，网络超时：",e.message.toString())
                    BaseResponse.error(-10,"登录网络超时")
                }
            }
            if (res != null){
                when(res.result_code){
                    0 -> {
                        // 登录成功
                        YwObject.loginUser = res.uedata as LoginUser
                        loginMessage.value = YW_OK
                        ElyApplication.context.getSharedPreferences("user", Context.MODE_PRIVATE).edit {
                            putInt("user_id",YwObject.loginUser.user_id)
                            putString("account",account.value)
                            putString("password",password.value)
                            putString("token",YwObject.loginUser.token)
                            putLong("time",System.currentTimeMillis())
                        }
                    }
                    else -> {
                        loginMessage.value = res.message
                    }
                }
            }
            else {
                loginMessage.value = "res is null"
            }
        }
    }
}