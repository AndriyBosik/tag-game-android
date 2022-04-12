package com.example.taggame.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.taggame.R
import com.example.taggame.databinding.FragmentGameBinding
import com.example.taggame.viewmodel.SharedPhotoViewModel

class GameFragment: Fragment(R.layout.fragment_game) {
    private val sharedViewModel: SharedPhotoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentGameBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game, container, false
        )
        binding.lifecycleOwner = this
        binding.vm = sharedViewModel

        return binding.root
    }
}