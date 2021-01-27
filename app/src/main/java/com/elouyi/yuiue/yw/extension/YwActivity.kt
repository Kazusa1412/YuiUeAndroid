package com.elouyi.yuiue.yw.extension

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.elouyi.yuiue.activity.LauncherActivity
import com.elouyi.yuiue.activity.MainActivity
import com.elouyi.yuiue.retrofit.LoginResponse
import com.elouyi.yuiue.retrofit.LoginService
import com.elouyi.yuiue.retrofit.ServerCreater
import com.elouyi.yuiue.util.launchActivity
import com.elouyi.yuiue.yw.YwObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun AppCompatActivity.login(account: String?,password: String?){
    ServerCreater.create<LoginService>().login(account, password)
        .enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val res = response.body()
                res?.let {
                    when (it.result_code) {
                        0 -> {
                            YwObject.loginUser = it.uedata
                            getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).edit {
                                putString("token", YwObject.loginUser.token)
                                putLong("tokenE", System.currentTimeMillis())
                            }

                        }
                        else -> {
                            // TODO: 2021/1/27 登录失败
                            Log.w("登录失败： ",it.message)
                        }
                    }
                }
                Log.e("登录错误:","res为空")
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                false
            }
        })
}