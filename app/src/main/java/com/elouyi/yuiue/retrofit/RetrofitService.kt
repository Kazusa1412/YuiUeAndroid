package com.elouyi.yuiue.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginService {

    @GET("login")
    fun login(
        @Query("account") account: String?,
        @Query("password") password: String?
    ): Call<LoginResponse>
}