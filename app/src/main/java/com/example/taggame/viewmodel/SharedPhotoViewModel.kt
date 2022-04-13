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
        private const val PIECES_COUNT = 3
        private const val PIECE_SIZE = 300
    }

    private lateinit var game: Game

    private val mutableNavigationEvent: MutableLiveData<NavigationEventType> = MutableLiveData()
    val navigationEvent: LiveData<NavigationEventType> = mutableNavigationEvent

    private val mutablePhoto: MutableLiveData<Bitmap> = MutableLiveData()
    val photo: LiveData<Bitmap> = mutablePhoto

    fun photoSelected(photo: Bitmap) {
        game = gameService.initGame(PIECES_COUNT, PIECE_SIZE, photo)
        mutablePhoto.value = game.getField()
        mutableNavigationEvent.postValue(NavigationEventType.START)
    }

    fun clicked(x: Float, y: Float) {
        val piece = gameService.moveClickedPiecePosition(game.pieceSize, x, y)
        game.movePiece(piece)
        mutablePhoto.value = game.getField()
    }
}