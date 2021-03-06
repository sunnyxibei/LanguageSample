package com.timeriver.languagesample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
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
     * 从功能上来看，Channel其实是生产者消费之协程快捷版实现
     */
    fun testChannel() {
        val channel = Channel<Int>(3)
        viewModelScope.launch {
            val producer = launch {
                List(5) {
                    channel.send(it)
                    Timber.d("MainViewModel, send $it")
                }
                channel.close()
                Timber.d("MainViewModel, close channel. ClosedForSend = ${channel.isClosedForSend} ClosedForReceive = ${channel.isClosedForReceive}")
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

    /**
     * sequence只能使用yield方法，不能使用其他suspend方法
     * 受 RestrictsSuspension 注解的约束，delay 不能在 SequenceScope 的扩展成员当中被调用，因而不能在序列生成器的协程体内调用了。
     * flow更像是一个集成了coroutines高阶api的sequence，惰性集合，冷数据流，有下游订阅的时候才开始生产
     * 而Channel，是热数据流，不依赖于接收端
     * 这个写法，有点像Dart呢。。。
     */
    fun testFlow() {
        val flow = flow {
            (1..3).forEach {
                emit(it)
                delay(1000L)
            }
            throw ArithmeticException("Div 0")
        }.flowOn(Dispatchers.IO)
        viewModelScope.launch {
            flow.catch {
                Timber.e(it, "MainViewModel, testFlow")
            }.onCompletion { cause: Throwable? ->
                Timber.d("MainViewModel, testFlow finally")
            }.collect {
                Timber.d("MainViewModel, testFlow $it")
            }
        }
        createFlow().launchIn(viewModelScope)
    }

    /**
     * 使用flow去组织并发suspend任务
     */
    fun testFlowWithSuspend() {
        //传统方式组织并发
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

        //asFlow方式组织并发，看源码这里其实等同于上面，也是通过forEach emit实现
        viewModelScope.launch {
            (0..100).map {
                delaySample()
            }.asFlow().onEach {
                Timber.d("MainViewModel, delay $it")
            }.onCompletion {
                Timber.d("MainViewModel, wait for delay finish")
            }.collect()
        }

        //这个更进一步，真并发，对flow的变化是铜鼓merge扩展方法实现，是并发式地组织
        viewModelScope.launch {
            (0..100).map {
                flow<String> { delaySample() }
            }.merge().onEach {
                Timber.d("MainViewModel, delay $it")
            }.onCompletion {
                Timber.d("MainViewModel, wait for delay finish")
            }.collect()
        }
    }

    private fun createFlow() = flow {
        (1..3).forEach {
            emit(it)
            delay(1000L)
        }
    }.onEach {
        //这个onEach方法可以在聚合函数以外的地方处理数据，作用上来讲应该是Interceptor拦截器
        Timber.d("MainViewModel, createFlow $it")
    }

    companion object {
        private const val SAMPLE_URL = "https://www.yuque.com/xytech/flutter/dg1wmw"
    }
}