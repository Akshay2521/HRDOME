package com.app.hrdrec.admin.roles.get_all_roles_response

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.databinding.AddRoleRowBinding
import com.app.hrdrec.home.getallmodules.ModuleData
import com.app.hrdrec.home.getallmodules.ModuleDataRoles


class AddRolesAdapter (var mList :ArrayList<ModuleDataRoles>) : RecyclerView.Adapter<AddRolesAdapter.AddRolesViewHolder>() {

//    var mList = mutableListOf<GetAllRolesResponseData>()
//    private var clickInterface: ClickInterfaceOrgan<GetAllRolesResponseData>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun updateAlbumData(ModuleDatas: ArrayList<ModuleDataRoles>) {
//        this.mList = ModuleDatas.toMutableList()
//        notifyItemRangeInserted(0, ModuleDatas.size)
        mList=ModuleDatas
        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddRolesViewHolder {
        val binding =
            AddRoleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddRolesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddRolesViewHolder, position: Int) {
        val mObj = mList[position]

        holder.binding.organizationName.text = mObj.name

       val adapter= PermissionRolesAdapter(mObj.paths)
       holder.binding.recyclerViewPath.adapter=adapter



    }

    override fun getItemCount(): Int {
        return mList.size
    }



    /*fun updateList(temp: ArrayList<GetAllRolesResponseData>) {
          mList=temp
        notifyDataSetChanged()
    }*/

    class AddRolesViewHolder(val binding: AddRoleRowBinding) : RecyclerView.ViewHolder(binding.root)
}

