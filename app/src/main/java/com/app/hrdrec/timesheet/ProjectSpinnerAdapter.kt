package com.app.hrdrec.timesheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.app.hrdrec.R
import com.app.hrdrec.databinding.ProjectDropDownSelectedBinding
import com.app.hrdrec.timesheet.response.project_response.ProjectEmployee


class ProjectSpinnerAdapter(var dataList: ArrayList<ProjectEmployee>) : BaseAdapter() {
    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): ProjectEmployee {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<ProjectDropDownSelectedBinding>(
            LayoutInflater.from(parent!!.context),
            R.layout.project_drop_down_selected,
            parent,
            false


        )
        binding.relation = dataList[position].projectName

        return binding.root
    }
}









