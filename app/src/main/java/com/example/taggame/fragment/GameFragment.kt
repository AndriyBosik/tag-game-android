package com.example.taggame.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.taggame.R
import com.example.taggame.databinding.FragmentGameBinding
import com.example.taggame.viewmodel.GameViewModel
import com.example.taggame.viewmodel.SharedPhotoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment: Fragment() {
    private val gameViewModel: GameViewModel by activityViewModels()
    private val sharedViewModel: SharedPhotoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentGameBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game, container, false)

        binding.sharedVM = sharedViewModel
        binding.gameVM = gameViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val image: ImageView = view.findViewById(R.id.ivTagImage)

        gameViewModel.photo.observe(this) {
            image.setImageBitmap(it)
        }

        image.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                gameViewModel.clicked(image.width, event.x, event.y)
            }
            true
        }
    }
}