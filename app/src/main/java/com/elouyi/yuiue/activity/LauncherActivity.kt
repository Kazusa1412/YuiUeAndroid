package com.elouyi.yuiue.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.elouyi.yuiue.R
import com.elouyi.yuiue.jetpack.viewModel.LauncherViewModel
import com.elouyi.yuiue.util.launchActivity
import com.elouyi.yuiue.yw.YW_OK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LauncherActivity: ElyActivity() {

    private val viewModel: LauncherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        init()
    }

    private fun init(){

        val sp = getSharedPreferences("user",Context.MODE_PRIVATE)
        val account = sp.getString("account","")
        val password = sp.getString("password","")
        val time = sp.getLong("time",0)
        if (time >= System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 3){
            viewModel.setAccount(account!!)
            viewModel.setPassword(password!!)
            viewModel.login()
        }else{
            viewModel.viewModelScope.launch {
                delay(2000L)
                launchActivity<LoginActivity>()
                finish()
            }
        }
        viewModel.loginMessage.observe(this){
            when(it){
                YW_OK -> {
                    launchActivity<MainActivity>()
                    finish()
                }
                else -> {
                    launchActivity<LoginActivity>()
                    finish()
                }
            }
        }
    }
}