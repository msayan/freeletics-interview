package com.hololo.app.squarerepo.utils

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource

object WaitUtils {

    private val DEFAULT_WAIT_TIME = 3000

    private var idlingResource: IdlingResource? = null

    @JvmOverloads
    fun waitTime(waitingTime: Int = DEFAULT_WAIT_TIME) {
        cleanupWaitTime()

        idlingResource = ElapsedTimeIdlingResource(waitingTime.toLong())
        idlingResource?.let { IdlingRegistry.getInstance().register(it) }
    }

    fun cleanupWaitTime() {
        idlingResource?.let { IdlingRegistry.getInstance().unregister(it) }
        idlingResource = null
    }
}
