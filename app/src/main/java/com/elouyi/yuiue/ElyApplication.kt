package com.elouyi.yuiue

import android.app.Application
import android.content.Context

class ElyApplication: Application(){

    companion object{
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}