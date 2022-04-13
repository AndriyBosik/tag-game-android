package com.example.taggame.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taggame.domain.Game
import com.example.taggame.meta.NavigationEventType
import com.example.taggame.service.GameService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class SharedPhotoViewModel @Inject constructor(private val gameService: GameService): ViewModel() {
    companion object {
        private const val PIECES_COUNT = 5
        private const val PIECE_SIZE = 300
        private const val SOLVED_MESSAGE = "Solved!"
        private const val NOT_SOLVED_MESSAGE = "Not Solved :("
    }

    private lateinit var game: Game

    private val mutableNavigationEvent: MutableLiveData<NavigationEventType> = MutableLiveData()
    val navigationEvent: LiveData<NavigationEventType> = mutableNavigationEvent

    private val mutablePhoto: MutableLiveData<Bitmap> = MutableLiveData()
    val photo: LiveData<Bitmap> = mutablePhoto

    private val mutableGameStatus: MutableLiveData<String> = MutableLiveData(NOT_SOLVED_MESSAGE)
    val gameStatus: LiveData<String> = mutableGameStatus

    fun photoSelected(photo: Bitmap) {
        game = gameService.initGame(PIECES_COUNT, PIECE_SIZE, photo)
        mutablePhoto.value = game.getField()
        mutableNavigationEvent.postValue(NavigationEventType.START)
    }

    fun clicked(fieldSize: Int, x: Float, y: Float) {
        val piece = gameService.moveClickedPiecePosition(fieldSize, game.fieldSize, x, y)
        game.movePiece(piece)
        mutablePhoto.value = game.getField()
        mutableGameStatus.value = mapGameSolutionToStatus(game.isSolved())
    }

    private fun mapGameSolutionToStatus(isSolved: Boolean): String {
        return if (isSolved) SOLVED_MESSAGE else NOT_SOLVED_MESSAGE
    }
}