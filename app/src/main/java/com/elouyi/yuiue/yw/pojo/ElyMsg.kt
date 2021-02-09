package com.elouyi.yuiue.yw.pojo

import com.elouyi.yuiue.yw.YwObject

class ElyMsg(
    val content: String,
    val type: Int,
    val timeStamp: Long,
    var from: String,
    var target: String
) {
    companion object{
        const val TYPE_GLOBAL = 1
        const val TYPE_FRIEND = 2
        const val TYPE_CHANGE_NAME = 3

        fun changeNameMsg(name: String): ElyMsg{
            return ElyMsg(
                name,
                TYPE_CHANGE_NAME,
                System.currentTimeMillis(),
                "changeName",
                "changeName",
            )
        }

        fun newMsg(content: String): ElyMsg{
            return ElyMsg(
                content,
                TYPE_GLOBAL,
                System.currentTimeMillis(),
                YwObject.loginUser.user_name,
                ""
            )
        }
    }

    override fun toString() =
        "{" +
        "\"content\":\"$content\"," +
        "\"type\":$type," +
        "\"timeStamp\":$timeStamp, " +
        "\"from\":\"${from}\"," +
        "\"target\":\"${target}\"" +
        "}"
}