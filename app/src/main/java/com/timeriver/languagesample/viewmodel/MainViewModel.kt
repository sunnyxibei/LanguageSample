package com.timeriver.languagesample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
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
}