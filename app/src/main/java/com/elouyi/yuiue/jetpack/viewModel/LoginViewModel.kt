package com.elouyi.yuiue.jetpack.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elouyi.yuiue.util.io.request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    val account = MutableLiveData<String>()

    val password = MutableLiveData<String>()

    fun setAccount(account: String){
        this.account.value = account
    }

    fun setPassword(password: String){
        this.password.value = password
    }

}