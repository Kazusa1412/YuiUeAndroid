package com.elouyi.yuiue.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProviders
import com.elouyi.yuiue.ElyApplication
import com.elouyi.yuiue.R
import com.elouyi.yuiue.databinding.ActivityLoginBinding
import com.elouyi.yuiue.jetpack.observer.YwObserver
import com.elouyi.yuiue.jetpack.viewModel.LoginViewModel

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
            if (it.toString().length < 6){
                binding.tilLoginAccount.error = "小于六个字儿"
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
        viewModel.account.observe(this){

        }
        viewModel.password.observe(this){

        }
    }
}