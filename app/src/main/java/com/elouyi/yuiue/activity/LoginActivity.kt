package com.elouyi.yuiue.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.elouyi.yuiue.R
import com.elouyi.yuiue.databinding.ActivityLoginBinding
import com.elouyi.yuiue.jetpack.observer.YwObserver
import com.elouyi.yuiue.jetpack.viewModel.LoginViewModel
import com.elouyi.yuiue.util.checkAccount
import com.elouyi.yuiue.util.checkPassword
import com.elouyi.yuiue.util.launchActivity
import com.elouyi.yuiue.yw.YW_OK
import com.elouyi.yuiue.yw.extension.showToast

class LoginActivity : ElyActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycle.addObserver(YwObserver())
        setViewEvent()
        progressDialog = ProgressDialog(this).apply {
            setCancelable(false)
            setTitle(resources.getString(R.string.logining))
            setMessage(resources.getString(R.string.loading))
        }
    }

    override fun onResume() {
        super.onResume()
        val sp = getSharedPreferences("user", MODE_PRIVATE)
        binding.etLoginAccount.setText(sp.getString("account",""))
        binding.etLoginPwd.setText(sp.getString("password",""))
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
            progressDialog.show()
            viewModel.login()
        }
        binding.btLoinZhuce.setOnClickListener {
            launchActivity<ZhuceActivity>()
        }
        viewModel.loginMessage.observe(this){
            progressDialog.dismiss()
            when(it){
                YW_OK -> {
                    launchActivity<MainActivity>()
                    finish()
                }
                else ->{
                    Log.w("登陆失败:",it)
                    "登陆失败 $it".showToast()
                }
            }
        }
        viewModel.account.observe(this){

        }
        viewModel.password.observe(this){

        }
    }
}