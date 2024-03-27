package com.app.hrdrec.admin.roles

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.hrdrec.admin.roles.get_all_roles_response.AddRolesAdapter
import com.app.hrdrec.admin.roles.get_all_roles_response.GetAllRolesResponseData
import com.app.hrdrec.databinding.ActivityAddRolesBinding
import com.app.hrdrec.home.getallmodules.ModuleDataRoles
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import com.google.gson.JsonArray
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddRoles : AppCompatActivity(), CommonInterface {

    var mList = arrayListOf<ModuleDataRoles>()

    private val rolesViewModel: RoleViewModel by viewModels()

    private val binding by lazy { ActivityAddRolesBinding.inflate(layoutInflater) }

    lateinit var adapter: AddRolesAdapter
    var from = "add"
    var roleId = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        rolesViewModel.setCallBacks(this)
        from = intent.getStringExtra("from")!!

        if (from == "add") {
            binding.btnSave.text = "Save"
        } else {
            binding.btnSave.text = "Update"

            val mObj = intent.getSerializableExtra("mObj") as GetAllRolesResponseData

            binding.apply {
                edtRoleName.setText(mObj.roleName)
                edtDescription.setText(mObj.roleDescription)
                roleId = mObj.roleId!!


            }
        }
        setObserver()
        rolesViewModel.getModuleList()

        binding.btnSave.setOnClickListener {

            if (binding.edtRoleName.text.toString().isEmpty()) {
                CommonMethods.showToast(this, "Please enter role name")
                return@setOnClickListener
            }
            if (binding.edtDescription.text.toString().isEmpty()) {
                CommonMethods.showToast(this, "Please enter description")
                return@setOnClickListener
            }



            JsonArray()
            val myList: List<Int>?
            //   val pathIds: List<Int>=ArrayList<Int>()
            myList = ArrayList()
            val locationId: List<Int>?
            locationId = ArrayList()
            locationId.add(3)
            for (i in mList.indices) {
                for (j in mList[i].paths) {
                    if (j.selected)
                    // json = json + "," + j.id
                        j.id?.let { it1 -> myList.add(it1) }
                }

            }

            // Log.e("sdffddf", "" + json)
            // val final = removeFirstChar(json)
            //   Log.e("sdffddf", "final " + final)

//            val jsonObjectInner = JSONObject()
//            jsonObjectInner.put("pathIds", final)
            //  jsonArrayInner.add(final)

            // val param = JSONObject()


            if (from == "add") {
                rolesViewModel.addRoles(
                    binding.edtRoleName.text.toString(),
                    binding.edtDescription.text.toString(),
                    myList,
                    locationId
                )
            } else {
                rolesViewModel.updateRoles(
                    binding.edtRoleName.text.toString(),
                    binding.edtDescription.text.toString(),
                    myList,
                    locationId,
                    roleId
                )
            }


            /*   for (i in mList.indices) {

               }
           })*/

        }
    }

    fun removeFirstChar(s: String): String {
        return s.substring(1)
    }

    private fun setObserver() {
        rolesViewModel.moduleData.observe(this) { mist ->
            mList = mist
            adapter = AddRolesAdapter(mList)
            binding.recyclerView.adapter = adapter


            // albumDataAdapter.updateAlbumData(mList)
            //  val adapter=ModuleDataAdapter(mList)

        }

        rolesViewModel.addLocaResponse.observe(this) {

            CommonMethods.showToast(this, "Roles added successfully")
            val intent = Intent()
            setResult(121, intent)
            finish()
        }

        rolesViewModel.updateRoleResponse.observe(this) {

            CommonMethods.showToast(this, "Roles updated successfully")
            val intent = Intent()
            setResult(121, intent)
            finish()
        }
    }


    override fun onResponseSuccess() {

    }

    override fun noListData() {

    }

    override fun onErrorMessage(message: String) {
        CommonMethods.showToast(this, message)
    }

    override fun showLoader() {
        CommonMethods.showLoader(this)
    }

    override fun hideLoader() {
        CommonMethods.hideLoader()
    }
}