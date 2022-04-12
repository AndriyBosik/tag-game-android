package com.example.taggame.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.taggame.R
import com.example.taggame.viewmodel.MenuViewModel
import com.example.taggame.viewmodel.SharedPhotoViewModel

class MenuFragment: Fragment() {
    private val sharedViewModel: SharedPhotoViewModel by activityViewModels()
    private val viewModel: MenuViewModel by viewModels()

    private val getContentMedia = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result != null && result.data != null && result.data!!.data != null) {
            sharedViewModel.photoSelected(result.data!!.data!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_menu, container, false)

        view.findViewById<Button>(R.id.bStart).setOnClickListener {
            viewModel.selectPhotoClicked()
        }

        sharedViewModel.navigationEvent.observe(this) {
            Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_gameFragment)
        }

        viewModel.dialogEvent.observe(this) {
            startPhotoSelection()
        }

        return view
    }

    private fun startPhotoSelection() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        getContentMedia.launch(intent)
    }
}