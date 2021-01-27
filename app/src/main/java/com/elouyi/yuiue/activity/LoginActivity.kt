package com.elouyi.yuiue.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import com.elouyi.yuiue.R
import com.elouyi.yuiue.databinding.ActivityLoginBinding
import com.elouyi.yuiue.jetpack.observer.YwObserver
import com.elouyi.yuiue.jetpack.viewModel.LoginViewModel
import com.elouyi.yuiue.retrofit.LoginResponse
import com.elouyi.yuiue.retrofit.NormalResponse
import com.elouyi.yuiue.retrofit.LoginService
import com.elouyi.yuiue.retrofit.ServerCreater
import com.elouyi.yuiue.util.checkAccount
import com.elouyi.yuiue.util.checkPassword
import com.elouyi.yuiue.util.launchActivity
import com.elouyi.yuiue.yw.YwObject
import com.elouyi.yuiue.yw.extension.login
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : ElyActivity() {

    lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycle.addObserver(YwObserver())
        setViewEvent()
    }

    private fun setViewEvent(){
        binding.etLoginAccount.addTextChangedListener {
            if (!checkAccount(it.toString())){
                binding.tilLoginAccount.error = resources.getString(R.string.accountFormError)
            }else{
                binding.tilLoginAccount.error = null
            }
            viewModel.setAccount(it.toString())
        }
        binding.etLoginPwd.addTextChangedListener {
            viewModel.setPassword(it.toString())
        }
        binding.etLoginPwd.setOnFocusChangeListener { _, hasFocus ->
            when {
                hasFocus -> {
                    binding.ivKasumi.setImageResource(R.mipmap.kasumi_close)
                }
                else -> {
                    binding.ivKasumi.setImageResource(R.mipmap.kasumi_open)
                }
            }
        }
        binding.btLogin.setOnClickListener {
            if (!checkAccount(binding.etLoginAccount.text.toString())
                || !checkPassword(binding.etLoginPwd.text.toString()))
                return@setOnClickListener
            login()
        }
        viewModel.account.observe(this){

        }
        viewModel.password.observe(this){

        }
    }
    fun login(){
        login(viewModel.account.value,viewModel.password.value)
    }
}