package com.example.taggame.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.taggame.meta.MergeDirection
import java.lang.IllegalArgumentException
import kotlin.math.min

class GraphicsUtils {
    companion object {
        fun addBorder(bitmap: Bitmap, borderSize: Float) {
            val canvas: Canvas = Canvas(bitmap)
            val p: Paint = Paint()
            p.color = Color.WHITE
            p.style = Paint.Style.STROKE
            p.strokeWidth = borderSize
            canvas.drawRect(0f, 0f, bitmap.width*1f, bitmap.height*1f, p)
        }

        fun merge(first: Bitmap?, second: Bitmap, direction: MergeDirection): Bitmap {
            if (first == null) {
                return second
            }
            ensureCorrectSize(first, second, direction)
            val height = if (direction == MergeDirection.HORIZONTAL) first.height else first.height + second.height
            val width = if (direction == MergeDirection.HORIZONTAL) first.width + second.width else first.width

            val merged = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas: Canvas = Canvas(merged)

            canvas.drawBitmap(first, 0f, 0f, null)
            canvas.drawBitmap(
                second,
                if (direction == MergeDirection.HORIZONTAL) first.width*1f else 0f,
                if (direction == MergeDirection.VERTICAL) first.height*1f else 0f,
                null)

            return merged
        }

        fun cutBitmap(bitmap: Bitmap, size: Int): Bitmap {
            val height = bitmap.height
            val width = bitmap.width

            val originalSize = min(height, width)

            val bm = Bitmap.createBitmap(
                bitmap,
                if (bitmap.width == originalSize) 0 else (bitmap.width - originalSize) / 2,
                if (bitmap.height == originalSize) 0 else (bitmap.height - originalSize) / 2,
                originalSize,
                originalSize
            )

            return Bitmap.createScaledBitmap(bm, size, size, false)
        }

        fun createColoredBitmap(color: Int, size: Int): Bitmap {
            val bitmap: Bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            canvas.drawColor(color)
            return bitmap
        }

        private fun ensureCorrectSize(first: Bitmap, second: Bitmap, direction: MergeDirection) {
            if (direction == MergeDirection.HORIZONTAL && first.height != second.height) {
                throw IllegalArgumentException()
            }
            if (direction == MergeDirection.VERTICAL && first.width != second.width) {
                throw IllegalArgumentException()
            }
        }
    }
}