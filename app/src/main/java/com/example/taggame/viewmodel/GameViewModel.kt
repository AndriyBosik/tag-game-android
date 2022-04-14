package com.example.taggame.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taggame.domain.Game
import com.example.taggame.meta.NavigationEventType
import com.example.taggame.model.GameData
import com.example.taggame.service.GameDataEventService
import com.example.taggame.service.GameService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class GameViewModel @Inject constructor(
    private val gameService: GameService,
    private val gameDataEventService: GameDataEventService
): ViewModel() {
    companion object {
        private const val PIECE_SIZE = 300
        private const val SOLVED_MESSAGE = "Solved!"
        private const val NOT_SOLVED_MESSAGE = "Not Solved :("
    }

    private var game: Game? = null

    private val mutablePhoto = MutableLiveData<Bitmap>()
    val photo: LiveData<Bitmap> = mutablePhoto

    private val mutableGameStatus: MutableLiveData<String> = MutableLiveData(NOT_SOLVED_MESSAGE)
    val gameStatus: LiveData<String> = mutableGameStatus

    private val fieldSizeObserver: (GameData) -> Unit = {
        if (game == null) {
            startGame(it)
        }
    }

    init {
        gameDataEventService.subscribe(fieldSizeObserver)
    }

    private fun startGame(gameData: GameData) {
        game = gameService.initGame(
            gameData.fieldSize,
            PIECE_SIZE,
            gameData.image)
        mutablePhoto.value = game!!.getField()
    }

    fun clicked(fieldSize: Int, x: Float, y: Float) {
        val piece = gameService.moveClickedPiecePosition(fieldSize, game!!.fieldSize, x, y)
        game!!.movePiece(piece)
        mutablePhoto.value = game!!.getField()
        mutableGameStatus.value = mapGameSolutionToStatus(game!!.isSolved())
    }

    private fun mapGameSolutionToStatus(isSolved: Boolean): String {
        return if (isSolved) SOLVED_MESSAGE else NOT_SOLVED_MESSAGE
    }

    override fun onCleared() {
        gameDataEventService.unsubscribe(fieldSizeObserver)
        super.onCleared()
    }
}