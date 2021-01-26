package com.elouyi.yuiue.activity

import android.os.Bundle
import com.elouyi.yuiue.R
import com.elouyi.yuiue.util.launchActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LauncherActivity: ElyActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        val scope = CoroutineScope(Job())
        scope.launch {
            delay(2000L)
            launchActivity<LoginActivity>()
            finish()
        }
    }
}