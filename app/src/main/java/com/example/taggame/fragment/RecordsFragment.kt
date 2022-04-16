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
import com.example.taggame.databinding.FragmentRecordsBinding
import com.example.taggame.model.Time
import com.example.taggame.viewmodel.RecordsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordsFragment: Fragment() {
    private val recordsViewModel: RecordsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentRecordsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_records, container, false)

        binding.vm = recordsViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val records = view.findViewById<ListView>(R.id.lvRecords)

        recordsViewModel.records.observe(this) {
            val adapter = SimpleAdapter(
                activity!!,
                buildData(it),
                R.layout.list_item_record,
                arrayOf("order", "time"),
                intArrayOf(R.id.tvOrder, R.id.tvTime))
            records.adapter = adapter
        }
    }

    private fun buildData(records: List<Time>): List<Map<String, String>> {
        val list = mutableListOf<Map<String, String>>()
        for (i in records.indices) {
            println("it: ${records[i].minutes}")
            val map = hashMapOf<String, String>()
            map["order"] = "${i + 1}."
            map["time"] = records[i].toString()
            list.add(map)
        }
        return list
    }
}