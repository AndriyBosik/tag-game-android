package com.example.taggame.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taggame.domain.Game
import com.example.taggame.domain.Timer
import com.example.taggame.meta.NavigationEventType
import com.example.taggame.model.GameData
import com.example.taggame.model.Time
import com.example.taggame.service.GameDataEventService
import com.example.taggame.service.GameService
import com.example.taggame.service.RecordService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class GameViewModel @Inject constructor(
    private val gameService: GameService,
    private val gameDataEventService: GameDataEventService,
    private val recordService: RecordService
): ViewModel() {
    companion object {
        private const val PIECE_SIZE = 300
    }

    private val timer: Timer = Timer()

    private var game: Game? = null

    private val mutablePhoto = MutableLiveData<Bitmap>()
    val photo: LiveData<Bitmap> = mutablePhoto

    private val mutableTime: MutableLiveData<Time> = MutableLiveData(Time(0, 0))
    val time: LiveData<Time> = mutableTime

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
        game!!.isSolved.observeForever {
            if (it) {
                timer.pause()
                viewModelScope.launch(Dispatchers.IO) {
                    recordService.saveRecord(time.value!!, game!!.fieldSize)
                }
            }
        }
        mutablePhoto.value = game!!.getField()
        timer.start()
        timer.time.observeForever {
            mutableTime.value = it
        }
    }

    fun clicked(fieldSize: Int, x: Float, y: Float) {
        val piece = gameService.moveClickedPiecePosition(fieldSize, game!!.fieldSize, x, y)
        game!!.movePiece(piece)
        mutablePhoto.value = game!!.getField()
    }

    override fun onCleared() {
        gameDataEventService.unsubscribe(fieldSizeObserver)
        super.onCleared()
    }
}