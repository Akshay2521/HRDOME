package com.app.hrdrec.organization.leavetypes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.databinding.LeavetypeRowListBinding
import com.app.hrdrec.organization.leavetypes.leavetype_models.LeavetypeData
import javax.inject.Inject

class LeavetypeDataAdapter @Inject constructor() :
    RecyclerView.Adapter<LeavetypeDataAdapter.LeavetypeViewHolder>() {

    var mList = mutableListOf<LeavetypeData>()
    private var clickInterface: ClickInterfaceLeavetype<LeavetypeData>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun updateAlbumData(ModuleDatas: ArrayList<LeavetypeData>) {
        this.mList = ModuleDatas.toMutableList()
        //notifyItemRangeInserted(0, ModuleDatas.size)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeavetypeViewHolder {
        val binding = LeavetypeRowListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeavetypeViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LeavetypeViewHolder, position: Int) {
        val mObj = mList[position]

        holder.binding.location.text = "Location Name  : ${mObj.locationName}"
        holder.binding.name.text = "Name : ${mObj.name}"
        holder.binding.numberofleaves.text = "Number of Leaves: ${mObj.numberOfDays}"
        holder.binding.frequency.text = "Frequency  : ${mObj.frequency}"

        holder.binding.edit.setOnClickListener {
            clickInterface?.onClick(mObj)
        }

        holder.binding.delete.setOnClickListener {
            clickInterface?.onClickDelete(mObj)
        }

//        Picasso
//            .get()
//            .load(mObj.url)
//            .into(holder.view.imgAlbum)
        holder.itemView.setOnClickListener {
            clickInterface?.onClick(mObj)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setItemClick(clickInterface: ClickInterfaceLeavetype<LeavetypeData>) {
        this.clickInterface = clickInterface
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(temp: ArrayList<LeavetypeData>) {
        mList = temp

        notifyDataSetChanged()
    }

    class LeavetypeViewHolder(val binding: LeavetypeRowListBinding) : RecyclerView.ViewHolder(binding.root)
}

interface ClickInterfaceLeavetype<T> {
    fun onClick(data: T)
    fun onClickDelete(data: T)
}