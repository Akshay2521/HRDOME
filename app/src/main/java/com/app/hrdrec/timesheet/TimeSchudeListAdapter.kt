package com.app.hrdrec.timesheet


import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.R
import com.app.hrdrec.databinding.LayoutTimeSheetDataBinding

import com.app.hrdrec.timesheet.response.TimeScheduleData


class TimeSchudeListAdapter(var mList: ArrayList<TimeScheduleData>, val featureCallback:FeatureCallBack) : RecyclerView.Adapter<TimeSchudeListAdapter.FeatureHolder>() {

    class FeatureHolder(val binding: LayoutTimeSheetDataBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureHolder {
        val mBinding = LayoutTimeSheetDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeatureHolder(mBinding)

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: FeatureHolder, @SuppressLint("RecyclerView") position: Int) {
        val mObj = mList[position]
        holder.binding.organizationName.text = "Location Name : ${mObj.locationName}"
        holder.binding.roleDescription.text = "Weekend Date : ${mObj.weekendDate}"
        holder.binding.roleName.text = mObj.status

        when(mObj.status)
        {
            "Approved"->{
                holder.binding.viewBg.setBackgroundColor(holder.itemView.context.getColor(R.color.card_bg_green))
            }
            "Submitted"->{
                holder.binding.viewBg.setBackgroundColor(holder.itemView.context.getColor(R.color.card_bg_blue))
            }

            "Saved"->{
                holder.binding.viewBg.setBackgroundColor(holder.itemView.context.getColor(R.color.app_orange))
            }
            "Rejected"->{
                holder.binding.viewBg.setBackgroundColor(holder.itemView.context.getColor(R.color.card_bg_red))
            }

        }

        holder.itemView.setOnClickListener {
            if (mObj.status != "Approved" && mObj.status != "Submitted") {
                mObj.weekendDate?.let { it1 -> featureCallback.selectedItem(it1, mObj.id) }
            }
        }

    }



    interface FeatureCallBack{

        fun selectedItem(week: String, id: Int?)
    }


}