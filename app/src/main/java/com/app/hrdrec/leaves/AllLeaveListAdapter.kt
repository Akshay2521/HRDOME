package com.app.hrdrec.leaves


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.R
import com.app.hrdrec.databinding.LayoutAllLeaveDataBinding
import com.app.hrdrec.leaves.response.LeaveModel


class AllLeaveListAdapter(var mList: ArrayList<LeaveModel>, val featureCallback:FeatureCallBack) : RecyclerView.Adapter<AllLeaveListAdapter.FeatureHolder>() {

    class FeatureHolder(val binding: LayoutAllLeaveDataBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureHolder {
        val mBinding = LayoutAllLeaveDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeatureHolder(mBinding)

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: FeatureHolder, @SuppressLint("RecyclerView") position: Int) {
        val mObj = mList[position]

        holder.binding.fromDates.text = "${mObj.fromDate} - ${mObj.toDate}"
        holder.binding.leaveType.text = "Leave Type : ${mObj.leavetypeName}"
        holder.binding.leaveReason.text = "Reason : ${mObj.reason}"
        if(mObj.remarks!=null)
        holder.binding.leaveRemark.text = "Remarks : ${mObj.remarks}"
        holder.binding.status.text ="   ${mObj.status}   "

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

        if (mObj.status == "Approved"||mObj.status == "Submitted")
            holder.binding.edit.visibility=View.GONE
        else
            holder.binding.edit.visibility=View.VISIBLE

        holder.itemView.setOnClickListener {
            // mObj.weekendDate?.let { it1 -> featureCallback.selectedItem(it1) }
            if (mObj.status != "Approved"&&mObj.status != "Submitted")
                featureCallback.selectedItem(mObj)
        }

    }



    interface FeatureCallBack{

        fun selectedItem(week: LeaveModel)
    }


}