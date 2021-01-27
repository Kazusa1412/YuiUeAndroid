package com.elouyi.yuiue.retrofit



data class BaseResponse<T> (
    var result_code: Int = 0,
    var message: String = "",
    var uedata: T,
){
    companion object{
        fun error(result_code: Int = -1,message: String): BaseResponse<*>{
            return BaseResponse(result_code,message,null)
        }
    }
}