package com.app.hrdrec.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.databinding.AllModuleRowBinding
import com.app.hrdrec.home.getallmodules.ModuleData

import javax.inject.Inject


class ModuleDataAdapter @Inject constructor() : RecyclerView.Adapter<ModuleDataAdapter.ModuleDataViewHolder>() {

    var mList = mutableListOf<ModuleData>()
    private var clickInterface: ClickInterface<ModuleData>? = null

    fun updateAlbumData(ModuleDatas: List<ModuleData>) {
        this.mList = ModuleDatas.toMutableList()
        notifyItemRangeInserted(0, ModuleDatas.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleDataViewHolder {
        val binding =
            AllModuleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ModuleDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ModuleDataViewHolder, position: Int) {
        val mObj = mList[position]
        holder.view.tvTitle.text = mObj.name

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

    fun setItemClick(clickInterface: ClickInterface<ModuleData>) {
        this.clickInterface = clickInterface
    }

    class ModuleDataViewHolder(val view: AllModuleRowBinding) : RecyclerView.ViewHolder(view.root)
}

interface ClickInterface<T> {
    fun onClick(data: T)
}