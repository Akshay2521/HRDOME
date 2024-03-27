package com.app.hrdrec.expenses


import android.annotation.SuppressLint
import android.content.Context
import android.opengl.Visibility
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.R
import com.app.hrdrec.databinding.LayoutExpenseUpdateListBinding
import com.app.hrdrec.expenses.dataclasses.Item


class ExpenseUpdateAdapter(private val context: Context, var mList: Array<Item>, val featureCallback: UpdateExpenseActivity,
                           val showaction:Boolean, val from:String) : RecyclerView.Adapter<ExpenseUpdateAdapter.ExpenseHolder>() {

    class ExpenseHolder(val binding: LayoutExpenseUpdateListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseHolder {
        val mBinding = LayoutExpenseUpdateListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        holder.binding.categoryName.text = "${mObj.categoryName}"
        holder.binding.type.text = "${mObj.typeName}"
        holder.binding.date.text = "${mObj.date}"
        holder.binding.applied.text = "${mObj.submittedAmount}"
        holder.binding.status.text = "${mObj.status}"


        if(from == "EditExpense"){
            holder.binding.linerApporve.visibility = View.GONE
            holder.binding.linerRemark.visibility = View.GONE
            holder.binding.checkBox.visibility = View.GONE
            holder.binding.statusLabel.text = "${mObj.status}"
        }
        //featureCallback.selectedItem(it1, mObj.id)
        //holder.binding.linerStatus.background

        if(mObj.status == "" || mObj.status == "Saved"){
            holder.binding.linerStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.color_saved))
        }else if (mObj.status == "Approved") {
            holder.binding.linerStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.color_approved))
        }else if (mObj.status == "Submitted") {
            holder.binding.linerStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.color_submitted))
        }else if (mObj.status == "Rejected") {
            holder.binding.linerStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.color_reject))
        }

        holder.binding.checkBox.isChecked = mObj.checked
        holder.binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            holder.binding.checkBox.isChecked = isChecked
            if(holder.binding.checkBox.isChecked){
                println("here from ${from}");
                mObj.checked = true;
//                if(from == "Authorize"){
//                    mObj.status = "Approved";
//                }else if(from == "Reimbursed"){
//                    mObj.status = "Reimbursed";
//                    println("here comes ${mObj.status}");
//                }
            }
        }

        if(from == "Authorize"){
            if(mObj.approvedAmount != null){
                holder.binding.approved.setText(mObj.approvedAmount.toString());
            }
            if(mObj.approvedRemarks != null){
                holder.binding.remark.setText(mObj.approvedRemarks.toString());
            }

        }else if(from == "Reimburse"){
            if(mObj.reimbursedAmount != null){
                holder.binding.approved.setText(mObj.reimbursedAmount.toString());
            }
            if(mObj.reimbursedRemarks != null){
                holder.binding.remark.setText(mObj.reimbursedRemarks.toString());
            }

        }

        // Update the model when "Approved" text changes
        holder.binding.approved.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val approvedAmount: Int? = s?.toString()?.toIntOrNull()

                if(from == "Authorize"){
                    mObj.approvedAmount = approvedAmount

                }else if(from == "Reimburse"){
                    mObj.reimbursedAmount = approvedAmount
                }


            }
        })


        holder.binding.remark.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if(from == "Authorize"){
                    mObj.approvedRemarks = s?.toString()
                }else if(from == "Reimburse"){
                    mObj.reimbursedRemarks = s?.toString()
                }
            }
        })

    }



    interface FeatureCallBack{

        fun selectedItem(week: String, id: Int?)
    }

    fun getUpdatedData(): Array<Item>{
        return mList
    }

}