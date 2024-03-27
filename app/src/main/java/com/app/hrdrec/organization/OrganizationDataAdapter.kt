package com.app.hrdrec.organization

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.databinding.AllModuleRowBinding
import com.app.hrdrec.home.getallmodules.Paths

import javax.inject.Inject


class OrganizationDataAdapter @Inject constructor() : RecyclerView.Adapter<OrganizationDataAdapter.OrganizationDataViewHolder>() {

    var mList = mutableListOf<Paths>()
    private var clickInterface: ClickInterfaceOrgan<Paths>? = null

    fun updateAlbumData(ModuleDatas: List<Paths>) {
        this.mList = ModuleDatas.toMutableList()
        notifyItemRangeInserted(0, ModuleDatas.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganizationDataViewHolder {
        val binding =
            AllModuleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrganizationDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrganizationDataViewHolder, position: Int) {
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

    fun setItemClick(clickInterface: ClickInterfaceOrgan<Paths>) {
        this.clickInterface = clickInterface
    }

    class OrganizationDataViewHolder(val view: AllModuleRowBinding) : RecyclerView.ViewHolder(view.root)
}

interface ClickInterfaceOrgan<T> {
    fun onClick(data: T)
}