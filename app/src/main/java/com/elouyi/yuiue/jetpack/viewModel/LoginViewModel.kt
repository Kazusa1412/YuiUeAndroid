package com.elouyi.yuiue.jetpack.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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