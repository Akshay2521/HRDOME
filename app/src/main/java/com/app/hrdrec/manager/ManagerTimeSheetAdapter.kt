package com.app.hrdrec.manager


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.databinding.LayoutTimeSheetManagerBinding
import com.app.hrdrec.manager.response.all_submitted_response.GetSubmitedManagerModel


class ManagerTimeSheetAdapter(var mList: ArrayList<GetSubmitedManagerModel>, val featureCallback:FeatureCallBack) : RecyclerView.Adapter<ManagerTimeSheetAdapter.FeatureHolder>() {

    class FeatureHolder(val binding: LayoutTimeSheetManagerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureHolder {
        val mBinding = LayoutTimeSheetManagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeatureHolder(mBinding)

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: FeatureHolder, @SuppressLint("RecyclerView") position: Int) {
        val mObj = mList[position]

        holder.binding.organizationName.text = "Employee Name : ${mObj.employeeName}"
        holder.binding.roleDescription.text = "Weekend Date : ${mObj.weekendDate}"
        holder.binding.projectName.text = "Project Name: ${mObj.projectName}"
        holder.binding.roleName.text = "Status: ${mObj.status}"

        if(mObj.status.equals("Approved"))
        {
            holder.binding.linApruvReject.visibility=View.GONE
        }
        else{
            holder.binding.linApruvReject.visibility=View.VISIBLE
        }

        holder.binding.imgApprove.setOnClickListener({

            featureCallback.selectedItem("approve",mObj)
        })

        holder.binding.imgReject.setOnClickListener({

            featureCallback.selectedItem("reject",mObj)
        })

        holder.itemView.setOnClickListener({
           // mObj.weekendDate?.let { it1 -> featureCallback.selectedItem(it1,mObj) }

            featureCallback.selectedItem("view",mObj)
        })

    }



    interface FeatureCallBack{

        fun selectedItem(week: String, id: GetSubmitedManagerModel)
    }


}