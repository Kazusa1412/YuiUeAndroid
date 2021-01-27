package com.elouyi.yuiue.yw.extension

import android.widget.Toast
import com.elouyi.yuiue.ElyApplication

/**
 * ## 构建 String 的工具函数
 * ### 例子
 * eg: val str = buildString { append("zz") }
 *
 * @param block 有 [StringBuilder] 作用域的函数
 */
inline fun buildString(block: StringBuilder.() -> Unit) = StringBuilder().run {
    block()
    toString()
}

/**
 * Android Toast 的工具函数
 *
 * @param duration Toast 显示的时间 [Toast.LENGTH_SHORT] or [Toast.LENGTH_LONG]
 */
fun String.showToast(duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(ElyApplication.context,this,duration).show()
}