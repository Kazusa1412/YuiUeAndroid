package com.elouyi.yuiue.yw

interface User : Comparable<User>{
    val user_id: Int
    val user_name: String
}

abstract class AbstractUser(
    override val user_id: Int,
    override val user_name: String,
    open val user_avatar: String,
    open val user_exp: Int,
    open val user_sign: String,
) : User

class LoginUser(
    user_id: Int,
    user_name: String,
    user_avatar: String,
    user_exp: Int,
    user_sign: String,
    val token: String,
) : AbstractUser(
    user_id,
    user_name,
    user_avatar,
    user_exp, user_sign){

    override fun compareTo(other: User): Int {
        return 0
    }

}