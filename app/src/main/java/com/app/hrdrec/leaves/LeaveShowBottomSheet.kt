package com.app.hrdrec.leaves

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.databinding.BottomSheetLayoutBinding
import com.app.hrdrec.leaves.response.LeaveBalanceData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LeaveShowBottomSheet(val data: ArrayList<LeaveBalanceData>) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = LeaveBalanceDataAdapter(data)
    }

}