package com.elouyi.yuiue

import com.elouyi.yuiue.util.io.request
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun zz(){
        val str = buildString {
            append("zzz\n")
            append("bbb")
        }
        val list = listOf("asdadassd","daswa","da","dasdd")
        val max = list.maxByOrNull { it.length }
        println(max)

        val a = listOf(1,2,3,45)
        val b = listOf(5,6,8,1,2,0)


    }

    @Test
    fun sd(){
        runBlocking {
            val res = request("http://127.0.0.1:8001/login?account=123456&password=pzz")
            println(res)
            if (res.isEmpty()){
                println("zz")
            }
        }
    }
}

