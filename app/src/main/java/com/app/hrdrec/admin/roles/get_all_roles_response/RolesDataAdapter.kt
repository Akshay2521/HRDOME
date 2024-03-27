package com.app.hrdrec.admin.roles.get_all_roles_response

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.databinding.RolesRowListBinding
import javax.inject.Inject


class RolesDataAdapter @Inject constructor() : RecyclerView.Adapter<RolesDataAdapter.RolesViewHolder>() {

    var mList = mutableListOf<GetAllRolesResponseData>()
    private var clickInterface: ClickInterfaceOrgan<GetAllRolesResponseData>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun updateAlbumData(ModuleDatas: List<GetAllRolesResponseData>) {
        this.mList = ModuleDatas.toMutableList()
      //  notifyItemRangeInserted(0, ModuleDatas.size)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RolesViewHolder {
        val binding =
            RolesRowListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RolesViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RolesViewHolder, position: Int) {
        val mObj = mList[position]

        holder.binding.organizationName.text = "organization Name : ${mObj.organizationName}"
        holder.binding.roleDescription.text = "Role Description : ${mObj.roleDescription}"
        holder.binding.roleName.text = "Role Name : ${mObj.roleName}"

//        Picasso
//            .get()
//            .load(mObj.url)
//            .into(holder.view.imgAlbum)
        holder.itemView.setOnClickListener {
            //clickInterface?.onClick(mObj)
        }
        holder.binding.edit.setOnClickListener {
            clickInterface?.onClick(mObj)

        }
        holder.binding.delete.setOnClickListener {
            clickInterface?.onClickDelete(mObj)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setItemClick(clickInterface: ClickInterfaceOrgan<GetAllRolesResponseData>) {
        this.clickInterface = clickInterface
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(temp: ArrayList<GetAllRolesResponseData>) {
          mList=temp
        notifyDataSetChanged()
    }

    class RolesViewHolder(val binding: RolesRowListBinding) : RecyclerView.ViewHolder(binding.root)
}

interface ClickInterfaceOrgan<T> {
    fun onClick(data: T)
    fun onClickDelete(data: T)
}