package com.example.taggame.domain

import android.graphics.Bitmap
import android.graphics.Color
import com.example.taggame.meta.MergeDirection
import com.example.taggame.model.PiecePosition
import com.example.taggame.utils.GraphicsUtils
import java.security.SecureRandom
import kotlin.math.abs

class Game(val fieldSize: Int, val pieceSize: Int, bitmap: Bitmap) {
    private val pieces = mutableListOf<MutableList<Bitmap>>()
    private val orders = mutableListOf<MutableList<Int>>()
    private var emptyPiece: PiecePosition = PiecePosition(0, 0)

    companion object {
        private const val STEPS_COUNT = 10000
        private val random: SecureRandom = SecureRandom()
    }

    init {
        divideByPieces(bitmap)
        initOrders()

        emptyPiece = PiecePosition(fieldSize - 1, fieldSize - 1)
    }

    fun shuffleField() {
        var currentStep: Int = 0
        while (currentStep < STEPS_COUNT) {
            val newPosition = makeStep()
            if (!emptyPosition(newPosition)) {
                emptyPiece = newPosition
                currentStep++
            }
        }
    }

    fun getField(): Bitmap {
        var image: Bitmap? = null

        for (i in 0 until fieldSize) {
            var leftBitmap: Bitmap? = null
            for (j in 0 until fieldSize) {
                val current = getPieceByOrder(orders[i][j])
                GraphicsUtils.addBorder(current, 5f)
                leftBitmap = GraphicsUtils.merge(leftBitmap, current, MergeDirection.HORIZONTAL)
            }
            image = GraphicsUtils.merge(image, leftBitmap!!, MergeDirection.VERTICAL)
        }

        return image!!
    }

    fun movePiece(position: PiecePosition) {
        if (areAdjacent(position, emptyPiece)) {
            swapPieces(emptyPiece, position)
            emptyPiece = position
        }
    }

    private fun getPieceByOrder(order: Int): Bitmap {
        if (order == fieldSize*fieldSize) {
            return GraphicsUtils.createColoredBitmap(Color.BLACK, pieceSize)
        }
        val piecePosition: PiecePosition = getPiecePositionByOrder(order)
        return pieces[piecePosition.i][piecePosition.j]
    }

    private fun getPiecePositionByOrder(order: Int): PiecePosition {
        val zeroBasedOrder = order - 1
        return PiecePosition(
            zeroBasedOrder / 3,
            zeroBasedOrder % 3
        )
    }

    private fun areAdjacent(first: PiecePosition, second: PiecePosition): Boolean {
        return abs(first.i - second.i) + abs(first.j - second.j) == 1
    }

    private fun makeStep(): PiecePosition {
        val id = random.nextInt(4)
        if (id == 0 && emptyPiece.i - 1 >= 0) {
            val newPosition = PiecePosition(emptyPiece.i - 1, emptyPiece.j)
            swapPieces(emptyPiece, newPosition)
            return newPosition
        } else if (id == 1 && emptyPiece.j + 1 < fieldSize) {
            val newPosition = PiecePosition(emptyPiece.i, emptyPiece.j + 1)
            swapPieces(emptyPiece, newPosition)
            return newPosition
        } else if (id == 2 && emptyPiece.i + 1 < fieldSize) {
            val newPosition = PiecePosition(emptyPiece.i + 1, emptyPiece.j)
            swapPieces(emptyPiece, newPosition)
            return newPosition
        } else if (id == 3 && emptyPiece.j - 1 >= 0) {
            val newPosition = PiecePosition(emptyPiece.i, emptyPiece.j - 1)
            swapPieces(emptyPiece, newPosition)
            return newPosition
        }
        return emptyPiece
    }

    private fun swapPieces(first: PiecePosition, second: PiecePosition) {
        val current = orders[first.i][first.j]
        orders[first.i][first.j] = orders[second.i][second.j]
        orders[second.i][second.j] = current
    }

    private fun emptyPosition(second: PiecePosition): Boolean {
        return emptyPiece.i == second.i && emptyPiece.j == second.j
    }

    private fun divideByPieces(bitmap: Bitmap) {
        val originBitmap: Bitmap = GraphicsUtils.cutBitmap(bitmap, fieldSize*pieceSize)
        for (i in 0 until fieldSize) {
            val rowPieces = mutableListOf<Bitmap>()
            for (j in 0 until fieldSize) {
                val first = Bitmap.createBitmap(originBitmap, j*pieceSize, i*pieceSize, pieceSize, pieceSize)
                rowPieces.add(first)
            }
            pieces.add(rowPieces)
        }
    }

    private fun initOrders() {
        for (i in 0 until fieldSize) {
            val rowOrders = mutableListOf<Int>()
            for (j in 0 until fieldSize) {
                rowOrders.add(i*fieldSize + j + 1)
            }
            orders.add(rowOrders)
        }
    }
}