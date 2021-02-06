package com.elouyi.yuiue.retrofit

import com.elouyi.yuiue.yw.LoginUser
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface LoginService {


    @GET("login")
    fun ueLogin(
        @Query("account") account: String?,
        @Query("password") password: String?
    ): Call<BaseResponse<LoginUser>>

    @POST("signup")
    fun ueZhuce(
        @Body map: @JvmSuppressWildcards Map<String,Any?>
    ): Call<BaseResponse<Any>>
}