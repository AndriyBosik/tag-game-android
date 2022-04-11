package com.example.taggame.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.taggame.R
import com.example.taggame.databinding.FragmentMenuBinding
import com.example.taggame.viewmodel.MenuViewModel

class MenuFragment: Fragment() {
    private val viewModel: MenuViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_menu, container, false)

        view.findViewById<Button>(R.id.bStart).setOnClickListener {
            viewModel.startClicked()
        }

        viewModel.navigationEvent.observe(this) {
            Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_gameFragment)
        }

        return view
    }
}