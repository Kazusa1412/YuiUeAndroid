package com.elouyi.yuiue.jetpack.viewModel

import com.elouyi.yuiue.yw.component.Loginable

class LauncherViewModel : Loginable() {
    fun setAccount(account: String){
        this.account.value = account
    }

    fun setPassword(password: String){
        this.password.value = password
    }


}