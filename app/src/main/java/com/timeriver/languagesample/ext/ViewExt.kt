package com.timeriver.languagesample.ext

import android.view.View

const val CLICK_THRESHOLD = 1000L

/**
 * throttle first click event during threshold time mill
 */
inline fun View.setOnLimitClickListener(
    clickThreshold: Long = CLICK_THRESHOLD,
    crossinline listener: (v: View?) -> Unit
) {
    this.setOnClickListener(object : View.OnClickListener {

        private var lastClickMill = 0L

        override fun onClick(v: View?) {
            val currentTimeMill = System.currentTimeMillis()
            if (currentTimeMill - lastClickMill > clickThreshold) {
                lastClickMill = currentTimeMill
                listener.invoke(v)
            }
        }
    })
}