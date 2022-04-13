package com.example.taggame.service

import android.graphics.Bitmap
import com.example.taggame.domain.Game
import com.example.taggame.model.PiecePosition

interface GameService {
    fun initGame(fieldSize: Int, pieceSize: Int, bitmap: Bitmap): Game

    fun moveClickedPiecePosition(fieldSize: Int, pieceCount: Int, x: Float, y: Float): PiecePosition
}