package com.app.hrdrec.manager


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.databinding.LayoutLeaveManagerBinding
import com.app.hrdrec.manager.all_leave_submitted.LeaveManagerData


class ManagerReviewLeaveAdapter(var mList: ArrayList<LeaveManagerData>, val featureCallback: FeatureCallBack) : RecyclerView.Adapter<ManagerReviewLeaveAdapter.FeatureHolder>() {

    class FeatureHolder(val binding: LayoutLeaveManagerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureHolder {
        val mBinding = LayoutLeaveManagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeatureHolder(mBinding)

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: FeatureHolder, @SuppressLint("RecyclerView") position: Int) {
        val mObj = mList[position]



        holder.binding.organizationName.text = "Employee Name : ${mObj.employeeName}"
        holder.binding.roleDescription.text = "From Date : ${mObj.fromDate}"
        holder.binding.toDate.text = "To Date: ${mObj.toDate}"
        holder.binding.leaveType.text = "Leave Type: ${mObj.leaveTypeName}"
        holder.binding.leaveReason.text = "Reason: ${mObj.reason}"
        holder.binding.status.text = "Status: ${mObj.status}"

        if(mObj.status.equals("Approved"))
        {
            holder.binding.linApruvReject.visibility= View.GONE
        }
        else{
            holder.binding.linApruvReject.visibility= View.VISIBLE
        }

        holder.binding.imgApprove.setOnClickListener {

            featureCallback.selectedItem("approve", mObj)
        }

        holder.binding.imgReject.setOnClickListener {

            featureCallback.selectedItem("reject", mObj)
        }



    }



    interface FeatureCallBack{

        fun selectedItem(week: String, mObj: LeaveManagerData)
    }


}