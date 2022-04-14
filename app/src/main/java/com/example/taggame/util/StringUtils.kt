package com.example.taggame.util

class StringUtils {
    companion object {
        @JvmStatic
        fun addZeroToBegin(value: Int): String {
            return if (value < 10) "0$value" else value.toString()
        }
    }
}