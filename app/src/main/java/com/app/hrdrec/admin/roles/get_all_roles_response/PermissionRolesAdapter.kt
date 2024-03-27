package com.app.hrdrec.admin.roles.get_all_roles_response

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.R
import com.app.hrdrec.databinding.AddrolepermissionRowBinding
import com.app.hrdrec.home.getallmodules.Paths


class PermissionRolesAdapter(var mList: ArrayList<Paths>) :
    RecyclerView.Adapter<PermissionRolesAdapter.PermissionViewHolder>() {

//    var mList = mutableListOf<GetAllRolesResponseData>()
//    private var clickInterface: ClickInterfaceOrgan<GetAllRolesResponseData>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun updateAlbumData(ModuleDatas: ArrayList<Paths>) {

        mList = ModuleDatas
        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PermissionViewHolder {
        val binding =
            AddrolepermissionRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PermissionViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: PermissionViewHolder, position: Int) {
        val mObj = mList[position]

        holder.binding.pathName.text = mObj.name

        if (mObj.selected) {
            holder.binding.imgSelected.setImageResource(R.drawable.ic_radio_button_checked)
        } else {
            holder.binding.imgSelected.setImageResource(R.drawable.ic_radio_button_unchecked)
        }

        holder.itemView.setOnClickListener {

            mObj.selected = !mObj.selected
            notifyDataSetChanged()
        }


    }

    override fun getItemCount(): Int {
        return mList.size
    }


    /*fun updateList(temp: ArrayList<GetAllRolesResponseData>) {
          mList=temp
        notifyDataSetChanged()
    }*/

    class PermissionViewHolder(val binding: AddrolepermissionRowBinding) :
        RecyclerView.ViewHolder(binding.root)
}

