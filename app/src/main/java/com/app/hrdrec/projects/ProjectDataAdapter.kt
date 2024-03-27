package com.app.hrdrec.projects

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.databinding.ProjectsRowListBinding

import javax.inject.Inject

class ProjectDataAdapter @Inject constructor() :
    RecyclerView.Adapter<ProjectDataAdapter.ProjectViewHolder>() {

    var mList = mutableListOf<ProjectData>()
    private var clickInterface: ClickInterfaceProjects<ProjectData>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun updateAlbumData(ModuleDatas: List<ProjectData>) {
        this.mList = ModuleDatas.toMutableList()
        //notifyItemRangeInserted(0, ModuleDatas.size)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val binding = ProjectsRowListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val mObj = mList[position]

        holder.binding.name.text = "name  : ${mObj.projectName}"
        holder.binding.technology.text = "technology : ${mObj.technology}"
        holder.binding.projectClientId.text = "Client: ${mObj.projectClientId}"
        holder.binding.expectedStartDate.text = "Expected Start Date  : ${mObj.expectedStartDate}"
        holder.binding.projectStatusId.text = "Frequency  : ${mObj.projectStatusName}"

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

    fun setItemClick(clickInterface: ClickInterfaceProjects<ProjectData>) {
        this.clickInterface = clickInterface
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(temp: ArrayList<ProjectData>) {
        mList = temp

        notifyDataSetChanged()
    }

    class ProjectViewHolder(val binding: ProjectsRowListBinding) : RecyclerView.ViewHolder(binding.root)
}

interface ClickInterfaceProjects<T> {
    fun onClick(data: T)
    fun onClickDelete(data: T)
}