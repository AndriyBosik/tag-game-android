package com.example.taggame.viewmodel

import android.graphics.Bitmap
import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taggame.meta.MergeDirection
import com.example.taggame.meta.NavigationEventType
import com.example.taggame.model.PiecePosition
import com.example.taggame.utils.GraphicsUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import java.security.SecureRandom
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
open class SharedPhotoViewModel @Inject constructor(): ViewModel() {
    companion object {
        private const val PIECES_COUNT = 3
        private const val PIECE_SIZE = 300
        private const val STEPS = 1000
        private val random = SecureRandom()
    }

    private var pieces = mutableListOf<MutableList<Bitmap>>()
    private var emptyPiece = PiecePosition(PIECES_COUNT - 1, PIECES_COUNT - 1)

    private val mutableNavigationEvent: MutableLiveData<NavigationEventType> = MutableLiveData()
    val navigationEvent: LiveData<NavigationEventType> = mutableNavigationEvent

    private val mutablePhoto: MutableLiveData<Bitmap> = MutableLiveData()
    val photo: LiveData<Bitmap> = mutablePhoto

    fun photoSelected(photo: Bitmap) {
        mutablePhoto.value = getBitmapPart(photo)
        mutableNavigationEvent.postValue(NavigationEventType.START)
    }

    fun clicked(x: Float, y: Float) {
        val i = y.toInt() / PIECE_SIZE
        val j = x.toInt() / PIECE_SIZE
        val clickedPiece = PiecePosition(i, j)
        swapPieces(pieces, clickedPiece, getFreePiece(i, j))
        mutablePhoto.value = mergeToBitmap()
    }

    private fun getFreePiece(i: Int, j: Int): PiecePosition {
        if (abs(i - emptyPiece.i) + abs(j - emptyPiece.j) == 1) {
            val toReturn = emptyPiece
            emptyPiece = PiecePosition(i, j)
            return toReturn
        }
        return PiecePosition(i, j)
    }

    private fun getBitmapPart(photo: Bitmap): Bitmap {
        val main: Bitmap = GraphicsUtils.cutBitmap(photo, PIECES_COUNT*PIECE_SIZE)

        pieces = mutableListOf()

        for (i in 0 until PIECES_COUNT) {
            val rowPieces = mutableListOf<Bitmap>()
            for (j in 0 until PIECES_COUNT) {
                val first = Bitmap.createBitmap(main, j*PIECE_SIZE, i*PIECE_SIZE, PIECE_SIZE, PIECE_SIZE)
                rowPieces.add(first)
            }
            pieces.add(rowPieces)
        }


        GraphicsUtils.fillWithColor(Color.BLACK, pieces[PIECES_COUNT - 1][PIECES_COUNT - 1])

        var position = PiecePosition(PIECES_COUNT - 1, PIECES_COUNT - 1)
        var currentStep = 0

        while (currentStep < STEPS) {
            val newPosition = makeStep(pieces, position)
            if (!samePosition(newPosition, position)) {
                position = newPosition
                currentStep++
            }
        }
        emptyPiece = position

        return mergeToBitmap()
    }

    private fun mergeToBitmap(): Bitmap {
        var image: Bitmap? = null

        for (i in 0 until PIECES_COUNT) {
            var leftBitmap: Bitmap? = null
            for (j in 0 until PIECES_COUNT) {
                val current = pieces[i][j]
                GraphicsUtils.addBorder(current, 5f)
                leftBitmap = GraphicsUtils.merge(leftBitmap, current, MergeDirection.HORIZONTAL)
            }
            image = GraphicsUtils.merge(image, leftBitmap!!, MergeDirection.VERTICAL)
        }

        return image!!
    }

    private fun makeStep(image: List<MutableList<Bitmap>>, position: PiecePosition): PiecePosition {
        val n = image.size

        val id = random.nextInt(4)
        if (id == 0 && position.i - 1 >= 0) {
            val newPosition = PiecePosition(position.i - 1, position.j)
            swapPieces(image, position, newPosition)
            return newPosition
        } else if (id == 1 && position.j + 1 < n) {
            val newPosition = PiecePosition(position.i, position.j + 1)
            swapPieces(image, position, newPosition)
            return newPosition
        } else if (id == 2 && position.i + 1 < n) {
            val newPosition = PiecePosition(position.i + 1, position.j)
            swapPieces(image, position, newPosition)
            return newPosition
        } else if (id == 3 && position.j - 1 >= 0) {
            val newPosition = PiecePosition(position.i, position.j - 1)
            swapPieces(image, position, newPosition)
            return newPosition
        }
        return position
    }

    private fun swapPieces(image: List<MutableList<Bitmap>>, start: PiecePosition, end: PiecePosition) {
        val current = image[start.i][start.j]
        image[start.i][start.j] = image[end.i][end.j]
        image[end.i][end.j] = current
    }

    private fun samePosition(first: PiecePosition, second: PiecePosition): Boolean {
        return first.i == second.i && first.j == second.j
    }
}