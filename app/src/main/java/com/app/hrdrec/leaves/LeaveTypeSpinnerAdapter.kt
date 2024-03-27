package com.app.hrdrec.leaves

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.app.hrdrec.R

import com.app.hrdrec.databinding.RegisterRelationDropDownBinding
import com.app.hrdrec.databinding.RegisterRelationDropDownSelectedBinding
import com.app.hrdrec.leaves.response.LeaveType


class LeaveTypeSpinnerAdapter(var data: ArrayList<LeaveType>) : BaseAdapter(),
    android.widget.SpinnerAdapter {
    var currentPosition = -1
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<RegisterRelationDropDownBinding>(
            LayoutInflater.from(parent!!.context),
            R.layout.register_relation_drop_down,
            parent,
            false
        )
//            if(position == 0)
//            {
//                binding.imgCheck.visibility = View.GONE
        binding.relation = data[position].name
//            }else{
//                binding.imgCheck.visibility = View.VISIBLE
        //  binding.relation= data.get(position).name
//            }

        if (position == 0)
            binding.constRow.visibility = View.GONE
        else
            binding.constRow.visibility = View.VISIBLE

        //  binding.imgCheck.setImageResource(if (position == currentPosition) R.drawable.ic_tick else R.drawable.ic_ring)
        return binding.root
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(p0: Int): Any {
        return data[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, p1: View?, parent: ViewGroup?): View {


        val binding = DataBindingUtil.inflate<RegisterRelationDropDownSelectedBinding>(
            LayoutInflater.from(parent!!.context),
            R.layout.register_relation_drop_down_selected,
            parent,
            false
        )
        if (position == 0) {
            binding.imgCheck.visibility = View.GONE
            binding.relation = data[position].name
        } else {
            binding.imgCheck.visibility = View.VISIBLE
            binding.relation = data[position].name
        }

        return binding.root
    }


    fun updateList(list: ArrayList<LeaveType>) {
        this.data = list

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun isEnabled(position: Int): Boolean {
        return position != 0

    }


}