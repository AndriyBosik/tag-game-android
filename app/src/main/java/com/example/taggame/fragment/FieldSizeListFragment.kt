package com.example.taggame.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.taggame.R
import com.example.taggame.databinding.FragmentFieldSizeListBinding
import com.example.taggame.viewmodel.FieldSizeListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FieldSizeListFragment: Fragment() {
    private val fieldSizeListViewModel: FieldSizeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentFieldSizeListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_field_size_list, container, false)

        binding.vm = fieldSizeListViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listView = view.findViewById<ListView>(R.id.lvFieldSizes)

        fieldSizeListViewModel.fieldSizes.observe(this) {
            val adapter = SimpleAdapter(
                activity!!,
                buildData(it),
                R.layout.list_item_field_size,
                arrayOf("left", "right"),
                intArrayOf(R.id.tvLeftValue, R.id.tvRightValue))
            listView.adapter = adapter
        }
    }

    private fun buildData(fieldSizes: List<Int>): List<Map<String, String>> {
        val list = mutableListOf<Map<String, String>>()
        for (fieldSize in fieldSizes) {
            val map = hashMapOf<String, String>()
            map["left"] = fieldSize.toString()
            map["right"] = fieldSize.toString()
            list.add(map)
        }
        return list
    }
}