package com.elouyi.yuiue

import android.os.Bundle
import com.elouyi.yuiue.activity.ElyActivity

class MainActivity : ElyActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}