package com.app.hrdrec.projects

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.databinding.ProjectResourcesRowListBinding

import javax.inject.Inject

class ProjectResourceDataAdapter @Inject constructor() :
    RecyclerView.Adapter<ProjectResourceDataAdapter.ProjectResourceViewHolder>() {

    var mList = mutableListOf<ProjectData>()
    private var clickInterface: ClickInterfaceProjectResource<ProjectData>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun updateAlbumData(ModuleDatas: List<ProjectData>) {
        this.mList = ModuleDatas.toMutableList()
        //notifyItemRangeInserted(0, ModuleDatas.size)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectResourceViewHolder {
        val binding = ProjectResourcesRowListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectResourceViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProjectResourceViewHolder, position: Int) {
        val mObj = mList[position]

        holder.binding.employeeName.text = "employeeName  : ${mObj.employeeName}"
        holder.binding.startDate.text = "startDate : ${mObj.startDate}"
        holder.binding.endDate.text = "endDate: ${mObj.endDate}"
        holder.binding.role.text = "role  : ${mObj.role}"

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

    fun setItemClick(clickInterface: ClickInterfaceProjectResource<ProjectData>) {
        this.clickInterface = clickInterface
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(temp: ArrayList<ProjectData>) {
        mList = temp

        notifyDataSetChanged()
    }

    class ProjectResourceViewHolder(val binding: ProjectResourcesRowListBinding) : RecyclerView.ViewHolder(binding.root)
}

interface ClickInterfaceProjectResource<T> {
    fun onClick(data: T)
    fun onClickDelete(data: T)
}