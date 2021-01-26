package com.elouyi.yuiue.retrofit

import com.elouyi.yuiue.yw.LoginUser

interface UeResponse{
    val result_code: Int
    val message: String
}

data class NormalResponse(override val result_code: Int, override val message: String, val uedata: Map<*,*>): UeResponse

data class LoginResponse(override val result_code: Int,override val message: String,val uedata: LoginUser): UeResponse