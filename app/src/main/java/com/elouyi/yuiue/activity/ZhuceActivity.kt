package com.elouyi.yuiue.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.elouyi.yuiue.R
import com.elouyi.yuiue.databinding.ActivityZhuceBinding
import com.elouyi.yuiue.jetpack.viewModel.ZhuceViewModel
import com.elouyi.yuiue.util.checkAccount
import com.elouyi.yuiue.util.checkPassword
import com.elouyi.yuiue.yw.YW_OK
import com.elouyi.yuiue.yw.extension.showToast

class ZhuceActivity : ElyActivity() {

    private lateinit var binding: ActivityZhuceBinding
    private val viewModel: ZhuceViewModel by viewModels()
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZhuceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewEvent()
        progressDialog = ProgressDialog(this).apply {
            setCancelable(false)
            setTitle("")
            setMessage(resources.getString(R.string.loading))
        }
    }

    private fun setViewEvent(){
        binding.etZhucAccount.addTextChangedListener {
            if (!checkAccount(it.toString())){
                binding.tilZhuceAccount.error = resources.getString(R.string.accountFormError)
            }
            else{
                binding.tilZhuceAccount.error = null
            }
            viewModel.setAccount(it.toString())
        }
        binding.etZhucPwd.addTextChangedListener {
            if (!checkPassword(it.toString())){
                binding.tilZhucePwd.error = resources.getString(R.string.passwordFormError)
            }else{
                binding.tilZhucePwd.error = null
            }
            viewModel.setPassword(it.toString())
        }
        binding.etZhuceUsername.addTextChangedListener {
            viewModel.setUsername(it.toString())
        }
        binding.btZhuce.setOnClickListener {
            if (!checkAccount(binding.etZhucAccount.text.toString())
                || !checkPassword(binding.etZhucPwd.text.toString())
                || binding.etZhuceUsername.toString().isEmpty())
                return@setOnClickListener
            progressDialog.show()
            viewModel.zhuce()
        }
        viewModel.zhuceMessage.observe(this){
            progressDialog.dismiss()
            when(it){
                YW_OK -> {
                    resources.getString(R.string.signupOk).showToast()
                    finish()
                }
                else -> {
                    Log.w("注册失败:",it)
                    "${resources.getString(R.string.signupFail)} $it".showToast()
                }
            }
        }
    }
}