package com.app.hrdrec.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.R
import com.app.hrdrec.databinding.AllModuleRowBinding
import com.app.hrdrec.databinding.HomeDrawerRowBinding
import com.app.hrdrec.home.getallmodules.ModuleData

import javax.inject.Inject


class HomeNavDataAdapter @Inject constructor() : RecyclerView.Adapter<HomeNavDataAdapter.ModuleDataViewHolder>() {

    var mList = mutableListOf<ModuleData>()
    private var clickInterface: ClickInterface<ModuleData>? = null

    fun updateAlbumData(ModuleDatas: List<ModuleData>) {
        this.mList = ModuleDatas.toMutableList()
        notifyItemRangeInserted(0, ModuleDatas.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleDataViewHolder {
        val binding =
            HomeDrawerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ModuleDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ModuleDataViewHolder, position: Int) {
        val mObj = mList[position]
        holder.view.tvTitle.text = mObj.name

       /* when(mObj.name)
        {
            "Leaves"->{
                holder.view.imgModule.setImageResource(R.drawable.ic_leave_adap)
            }
            "Timesheets"->{
                holder.view.imgModule.setImageResource(R.drawable.ic_time_sheet_adap)
            }

            "Expenses"->{
                holder.view.imgModule.setImageResource(R.drawable.ic_expense_adap)
            }
           else-> {
                holder.view.imgModule.setImageResource(R.drawable.ic_leave_adap)
            }

        }*/

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

    class ModuleDataViewHolder(val view: HomeDrawerRowBinding) : RecyclerView.ViewHolder(view.root)
}

