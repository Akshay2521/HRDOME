package com.app.hrdrec.admin.users

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.admin.users.user_models.UserData
import com.app.hrdrec.databinding.RolesRowListBinding
import com.app.hrdrec.databinding.UserRowListBinding

import javax.inject.Inject


class UserDataAdapter @Inject constructor() :
    RecyclerView.Adapter<UserDataAdapter.UserViewHolder>() {

    var mList = mutableListOf<UserData>()
    private var clickInterface: ClickInterfaceUser<UserData>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun updateAlbumData(ModuleDatas: List<UserData>) {
        this.mList = ModuleDatas.toMutableList()
        //notifyItemRangeInserted(0, ModuleDatas.size)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserRowListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val mObj = mList[position]

        holder.binding.username.text = "username  : ${mObj.username}"
        holder.binding.organizationName.text = "organization Name : ${mObj.organizationName}"
        holder.binding.email.text = "Email: ${mObj.email}"
        holder.binding.locationName.text = "Location Name  : ${mObj.locationName}"
        holder.binding.phoneNumber.text = "Phone Number  : ${mObj.phoneNumber}"
        holder.binding.email.text = "Email: ${mObj.email}"
        holder.binding.employeeName.text = "Employee Name : ${mObj.employeeName}"
        holder.binding.roleName.text = "Role Name: ${mObj.roleName}"

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

    fun setItemClick(clickInterface: ClickInterfaceUser<UserData>) {
        this.clickInterface = clickInterface
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(temp: ArrayList<UserData>) {
        mList = temp

        notifyDataSetChanged()
    }

    class UserViewHolder(val binding: UserRowListBinding) : RecyclerView.ViewHolder(binding.root)
}

interface ClickInterfaceUser<T> {
    fun onClick(data: T)
    fun onClickDelete(data: T)
}