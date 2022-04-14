package com.example.taggame.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.taggame.R
import com.example.taggame.viewmodel.GameViewModel
import com.example.taggame.viewmodel.SharedPhotoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val sharedPhotoViewModel: SharedPhotoViewModel by viewModels()
    val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}