package com.example.taggame.model

import com.example.taggame.util.StringUtils

data class Time(val minutes: Int, val seconds: Int) {
    override fun toString(): String {
        return "$minutes:${StringUtils.addZeroToBegin(seconds)}"
    }
}