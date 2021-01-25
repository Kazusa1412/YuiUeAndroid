package com.elouyi.yuiue.util

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.elouyi.yuiue.ElyApplication
import java.security.MessageDigest

/**
 * md5 的工具函数
 */
fun md5(str: String) : String{
    try {
        val instance : MessageDigest = MessageDigest.getInstance("MD5")
        val digest : ByteArray = instance.digest(str.toByteArray())
        val sb = StringBuffer()
        for (b in digest){
            val i : Int = b.toInt() and 0xff
            var hexString = Integer.toHexString(i)
            if (hexString.length < 2){
                hexString = "0$hexString"
            }
            sb.append(hexString)
        }
        return sb.toString()
    }catch (e : Exception){
        e.printStackTrace()
    }
    return ""
}

/**
 * 启动一个带 [Intent] 参数的 Activity
 * eg：launchActivity<MainActivity>()
 * eg: launchActivity<MainActivity>{ putExtra("p","v") }
 */
inline fun <reified T: AppCompatActivity> AppCompatActivity.launchActivity(block: Intent.() -> Unit = {}) = Intent(this,T::class.java).run {
    block()
    this@launchActivity.startActivity(this)
}



