package com.app.hrdrec.expenses


import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.R
import com.app.hrdrec.databinding.LayoutExpenseDataBinding
import com.app.hrdrec.databinding.LayoutTimeSheetDataBinding



class ExpenseAdapter(private val context: Context, var mList: ArrayList<ExpenseData>, val featureCallback:FeatureCallBack,val showaction:Boolean) : RecyclerView.Adapter<ExpenseAdapter.ExpenseHolder>() {

    class ExpenseHolder(val binding: LayoutExpenseDataBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseHolder {
        val mBinding = LayoutExpenseDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpenseHolder(mBinding)

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ExpenseHolder, @SuppressLint("RecyclerView") position: Int) {
        val mObj = mList[position]
        //holder.binding.organizationName.text = "Location Name : ${mObj.locationName}"
        //holder.binding.roleDescription.text = "Weekend Date : ${mObj.weekendDate}"
        holder.binding.statusLabel.text = "${mObj.status}"
        holder.binding.expId.text = "${mObj.sheetId}"
        holder.binding.submitDate.text = "${mObj.submittedDate}"
        //holder.binding.authorizedBy.text = "${mObj.}"
        holder.binding.reimbursedBy.text = "${mObj.reimbursedName}"
        if(showaction){
            if(mObj.status == "Saved" || mObj.status == "Rejected"){
                holder.binding.linerAction.visibility = View.VISIBLE
            }else{
                holder.binding.linerAction.visibility = View.GONE
            }
        }else{
            holder.binding.linerAction.visibility = View.GONE
        }

        //featureCallback.selectedItem(it1, mObj.id)
        //holder.binding.linerStatus.background
        holder.binding.txtAmount.text = mObj.submittedAmount.toString();
        if(mObj.status == "" || mObj.status == "Saved"){
            holder.binding.linerStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.color_saved))
            holder.binding.lblAmount.text = "Saved Amount :";
        }else if (mObj.status == "Approved") {
            holder.binding.linerStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.color_approved))
            holder.binding.lblAmount.text = "Approved Amount :";
            holder.binding.txtAmount.text = mObj.approvedAmount.toString();
        }else if (mObj.status == "Submitted") {
            holder.binding.linerStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.color_submitted))
            holder.binding.lblAmount.text = "Submitted Amount :";
        }else if (mObj.status == "Rejected") {
            holder.binding.linerStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.color_reject))
        }else if (mObj.status == "Reimbursed") {
            holder.binding.lblAmount.text = "Reimbursed Amount :";
            holder.binding.txtAmount.text = mObj.reimbursedAmount.toString();

        }




        holder.binding.delete.setOnClickListener {
            featureCallback.selectedItem("delete", mObj.sheetId)
        }

        holder.binding.edit.setOnClickListener {
            featureCallback.selectedItem("edit", mObj.sheetId)

        }

        holder.itemView.setOnClickListener {
//            if (mObj.status != "Approved" && mObj.status != "Submitted") {
//                mObj.weekendDate?.let { it1 -> featureCallback.selectedItem(it1, mObj.id) }
//            }
            println("click happen");
            featureCallback.selectedItem("update", position)
        }

    }



    interface FeatureCallBack{

        fun selectedItem(week: String, id: Int?)
    }


}