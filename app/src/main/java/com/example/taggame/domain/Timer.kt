package com.example.taggame.domain

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taggame.model.Time

class Timer {
    private val timerHandler = Handler(Looper.getMainLooper())
    private val secondIncrementCallback = object: Runnable {
        override fun run() {
            val newTime = nextSecond(time.value!!)
            mutableTime.value = newTime
            timerHandler.postDelayed(this, 1000)
        }
    }

    private val mutableTime = MutableLiveData(Time(0, 0))
    val time: LiveData<Time> = mutableTime

    fun start() {
        timerHandler.post(secondIncrementCallback)
    }

    fun pause() {
        timerHandler.removeCallbacks(secondIncrementCallback)
    }

    private fun nextSecond(time: Time): Time {
        val seconds = convertTimeToSeconds(time)
        return convertSecondsToTime(seconds + 1)
    }

    private fun convertTimeToSeconds(time: Time): Int {
        return time.minutes*60 + time.seconds
    }

    private fun convertSecondsToTime(seconds: Int): Time {
        return Time(
            seconds / 60,
            seconds % 60)
    }
}