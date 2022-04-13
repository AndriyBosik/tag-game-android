package com.example.taggame.service.impl

import android.graphics.Bitmap
import com.example.taggame.domain.Game
import com.example.taggame.model.PiecePosition
import com.example.taggame.service.GameService
import javax.inject.Inject

class DefaultGameService @Inject constructor(): GameService {
    override fun initGame(fieldSize: Int, pieceSize: Int, bitmap: Bitmap): Game {
        val game: Game = Game(fieldSize, pieceSize, bitmap)
        game.shuffleField()
        return game
    }

    override fun moveClickedPiecePosition(fieldSize: Int, pieceCount: Int, x: Float, y: Float): PiecePosition {
        val pieceSize = fieldSize / pieceCount
        val i = y.toInt() / pieceSize
        val j = x.toInt() / pieceSize
        return PiecePosition(i, j)
    }
}