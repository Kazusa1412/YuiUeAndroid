package com.elouyi.yuiue.util.io

import com.elouyi.yuiue.util.YwCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun downloadFile(address: String,path: String,filename: String): File {
    val file = File((if(path.endsWith("/")) path else "$path/") + filename)
    if (file.exists()){
        return file
    }

    return withContext(Dispatchers.IO){
        val ss = URL(address).openConnection() as HttpURLConnection
        // 伪装成浏览器
        ss.addRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36")
        val ins = ss.inputStream
        val fos = FileOutputStream(file)
        var len = 0
        val buffer = ByteArray(1024)
        while (ins.read(buffer).also { len = it } != -1){
            fos.write(buffer,0,len)
        }
        fos.close()
        ins.close()
        file
    }
}

suspend fun downloadFile(address: String,path: String) : File{
    val suffix = address.substring(address.lastIndexOf('/') + 1, address.length)
    return downloadFile(address,path,suffix)
}


suspend fun request(address: String): String{
    return suspendCoroutine { continuation ->
        sendHttpRequest(address, object : YwCallback {
            override fun onSuccess(data: String) {
                continuation.resume(data)
            }
            override fun onFailure(e: java.lang.Exception) {
                continuation.resumeWithException(e)
            }
        })
    }
}

private fun sendHttpRequest(address: String,callback: YwCallback){
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(address)
        .build()
    val response: String?
    try {
        response = client.newCall(request).execute().body?.string()
        if (response != null){
            callback.onSuccess(response)
        }else{
            callback.onFailure(Exception("response is null"))
        }
    }catch (e: Exception){
        callback.onFailure(e)
    }
}