package com.example.taggame.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.taggame.R
import com.example.taggame.databinding.FragmentGameBinding
import com.example.taggame.databinding.FragmentMenuBinding
import com.example.taggame.model.DisplaySize
import com.example.taggame.viewmodel.MenuViewModel
import com.example.taggame.viewmodel.SharedPhotoViewModel
import java.io.InputStream

class MenuFragment: Fragment() {
    private val sharedViewModel: SharedPhotoViewModel by activityViewModels()
    private val viewModel: MenuViewModel by viewModels()

    private val getContentMedia = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result != null && result.data != null && result.data!!.data != null) {
            val imageStream: InputStream = context!!.contentResolver.openInputStream(result.data!!.data!!)!!
            val bitmap: Bitmap = BitmapFactory.decodeStream(imageStream)

            sharedViewModel.photoSelected(bitmap)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentMenuBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_menu, container, false)

        binding.vm = sharedViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.bStart).setOnClickListener {
            viewModel.selectPhotoClicked()
        }

        sharedViewModel.navigationEvent.observe(this) {
            Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_gameFragment)
        }

        viewModel.dialogEvent.observe(this) {
            startPhotoSelection()
        }
    }

    private fun startPhotoSelection() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        getContentMedia.launch(intent)
    }
}