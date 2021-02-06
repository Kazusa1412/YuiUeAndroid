package com.elouyi.yuiue.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.elouyi.yuiue.R
import com.elouyi.yuiue.util.launchActivity

class MainActivity : ElyActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId){
        R.id.item_chatRoom -> {
            launchActivity<ChatRoomActivity>()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}