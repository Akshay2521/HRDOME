package com.app.hrdrec.leaves

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.databinding.BalanceRowListBinding
import com.app.hrdrec.leaves.response.LeaveBalanceData


class LeaveBalanceDataAdapter(val mList: ArrayList<LeaveBalanceData>) : RecyclerView.Adapter<LeaveBalanceDataAdapter.RolesViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RolesViewHolder {
        val binding =
            BalanceRowListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RolesViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RolesViewHolder, position: Int) {
        val mObj = mList[position]

        holder.binding.organizationName.text = "${mObj.leaveTypeName}"
        holder.binding.roleDescription.text = "Credited : ${mObj.totalCredited}"
        holder.binding.roleName.text = "Used : ${mObj.totalUsed}"
        holder.binding.balance.text = "Balance : ${mObj.remaining}"


    }

    override fun getItemCount(): Int {
        return mList.size
    }



    class RolesViewHolder(val binding: BalanceRowListBinding) : RecyclerView.ViewHolder(binding.root)
}

