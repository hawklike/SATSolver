package util

import java.lang.management.ManagementFactory
import java.lang.management.ThreadMXBean

class StopwatchCPU(private val units: Double) {

    private val threadTimer: ThreadMXBean = ManagementFactory.getThreadMXBean()
    private val start: Long = threadTimer.currentThreadCpuTime

    fun elapsedTime(): Double {
        val now = threadTimer.currentThreadCpuTime
        return (now - start) / units
    }

    companion object Units {
        const val IN_SECONDS = 1000000000.0
        const val IN_MILLISECONDS = 1000000.0
        const val IN_MICROSECONDS = 1000.0
    }
}