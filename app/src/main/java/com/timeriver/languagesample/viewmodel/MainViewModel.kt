package com.timeriver.languagesample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import timber.log.Timber
import java.net.URL
import kotlin.random.Random

/**
 * 明天完全掌握coroutines的概念和用法
 */
class MainViewModel : ViewModel() {

    fun testCoroutinesBlock() {
        viewModelScope.launch {
            (0..100).map {
                Timber.d("MainViewModel, $it delay ${delaySample()}")
            }
            Timber.d("MainViewModel, wait for delay finish")
        }
    }

    /**
     * 这种写法只是为了验证async和await功能而写
     * 逻辑上和withContext(Dispatchers.Default)没有任何区别
     * 也就是说和上面的写法没有区别
     * 因为在下一个任务创建前，已经await，所以其实还是顺序执行
     */
    fun testCoroutinesBlockWithAsync() {
        viewModelScope.launch {
            (0..100).map {
                async {
                    Timber.d("MainViewModel, $it delay ${delaySample()}")
                }.await()
            }
            Timber.d("MainViewModel, wait for delay finish")
        }
    }

    /**
     * 异步组织方式一，可以在所以异步任务结束后统一执行回调操作
     */
    fun testCoroutinesNonBlockWithAsync() {
        viewModelScope.launch {
            (0..100).map {
                async {
                    Timber.d("MainViewModel, $it delay ${delaySample()}")
                }
            }.forEach {
                it.await()
            }
            Timber.d("MainViewModel, wait for delay finish")
        }
    }

    /**
     * 异步组织方式二，执行任务，没有在结束时组织回调
     * 这也就是async和launch的区别
     * 另外，suspend既可以修饰方法，也可以修饰代码块
     */
    fun testCoroutinesNonBlockWithLaunch() {
        (0..100).map {
            viewModelScope.launch {
                Timber.d("MainViewModel, $it delay ${delaySample()}")
            }
        }
        Timber.d("MainViewModel, no wait for delay finish")
    }

    private suspend fun delaySample(): String {
        val nextLong = Random.nextLong(10)
        delay(nextLong * 1000L)
        return "${nextLong}s"
    }

    fun testWithContext() {
        viewModelScope.launch {
            Timber.d("MainViewModel, testWithContext")
            Timber.d("MainViewModel, result ${withContextSample()}")
        }
    }

    /**
     * 从功能的角度上看，withContext和async一样，可以返回结果
     * 通过源码可以发现，async是一个DeferredCoroutine
     * withContext会阻塞执行block代码块
     */
    private suspend fun withContextSample(): String {
        return withContext(Dispatchers.IO) {
            runCatching { URL(SAMPLE_URL).readText() }.getOrDefault("失败了")
        }
    }

    fun testCoroutineStart() {
        viewModelScope.launch {
            Timber.d("MainViewModel, testCoroutineStart coroutineContext = ${coroutineContext[Job]}")
            logSample("1")
            //start 模式为lazy时，懒汉式，launch后不会有任何调度行为，协程block（代码块）也不会进入执行状态，直到我们需要它执行（job.start|join）的时候
            val job = launch {
                logSample("2")
            }
            //注意，withContext是阻塞获取result的
//            withContext(Dispatchers.Default) {
//                logSample("2")
//            }
            logSample("3")
            //join方法，通常用来调度任务执行顺序
//            job.join()
            logSample("4")
        }
    }

    private fun logSample(text: String) {
        Timber.d("MainViewModel, ${Thread.currentThread().name} logSample $text")
    }

    /**
     * Sequence 惰性集合
     */
    fun testSequence() {
        val fibonacci = sequence {
            yield(1L) // first Fibonacci number
            var cur = 1L
            var next = 1L
            while (true) {
                yield(next) // next Fibonacci number
                val tmp = cur + next
                cur = next
                next = tmp
            }
        }
        fibonacci.take(5).forEach {
            Timber.d("MainViewModel, fibonacci $it")
        }
    }

    /**
     * 从功能上来看，Channel其实是生产者消费之
     */
    fun testChannel() {
        val channel = Channel<Int>()
        viewModelScope.launch {
            val producer = launch {
                var i = 0
                while (isActive) {
                    channel.send(i++)
                    delay(1000L)
                }
            }
            val consumer = launch {
                for (element in channel) {
                    Timber.d("MainViewModel, element = $element")
                    delay(2000L)
                }
            }
            producer.join()
            consumer.join()
        }
    }

    companion object {
        private const val SAMPLE_URL = "https://www.yuque.com/xytech/flutter/dg1wmw"
    }
}