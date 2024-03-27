package com.app.hrdrec.admin.users

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.app.hrdrec.Url
import com.app.hrdrec.admin.users.employes_response.EmployeeListResponse
import com.app.hrdrec.admin.users.user_models.UserData
import com.app.hrdrec.databinding.ActivityAssignUsersBinding
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import com.app.hrdrec.admin.roles.get_all_roles_response.GetAllRolesResponseData
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class AssignUsers : AppCompatActivity(), CommonInterface {

    private val binding by lazy { ActivityAssignUsersBinding.inflate(layoutInflater) }

    private val userViewModel: UserViewModel by viewModels()

    var mListRoles = arrayListOf<GetAllRolesResponseData>()
    var mListEmployees = arrayListOf<EmployeeListResponse>()

    private lateinit var rolesSpinner: RolesSpinnerAdapter
    private lateinit var employeeSpinner: EmployeeSpinnerAdapter

    var employeeId: Int = 0
    var roleId: Int = 0
    var id: Int = 0
    var from = "add"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        userViewModel.setCallBacks(this)
        from = intent.getStringExtra("from")!!
        if (from == "add") {
            binding.btnSaveUser.text = "Save"
        } else {
            binding.btnSaveUser.text = "Update"

            val mObj = intent.getSerializableExtra("mObj") as UserData

            binding.apply {
                edtUserName.setText(mObj.username)
                try {
                    id = mObj.id!!
                    employeeId = mObj.employeeId!!
                    roleId = mObj.roleId!!

                } catch (_: Exception) {

                }
            }
        }


        setObserver()
        userViewModel.getAllEmployees()

        userViewModel.getAllRoles()

        binding.btnSaveUser.setOnClickListener {

            if (binding.edtUserName.text.toString().isEmpty()) {
                CommonMethods.showToast(this, "Please enter user name")
                return@setOnClickListener
            }

            if (from == "add") {
                val mObj = AddUserPayload(
                    binding.edtUserName.text.toString(),
                    roleId,
                    employeeId,
                    userViewModel.sharedPreferences.getInt(
                        Url.ORGANISATIONID
                    )
                )

                userViewModel.addUser(mObj)
            } else {
                val mObj = UpdateUserPayload(
                    binding.edtUserName.text.toString(),
                    roleId,
                    employeeId,
                    userViewModel.sharedPreferences.getInt(
                        Url.ORGANISATIONID
                    ),
                    id

                )
                userViewModel.UpdateUser(mObj)
            }
        }


        binding.roles.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {

                Log.e("mallal", "dds " + mListRoles[position].roleName)
                //Log.e("mallal","dds "+ mListRoles[position].roleId)
                mListRoles[position].roleId?.let {
                    roleId = it
                }
                // if(mListRoles[position].roleId!=null)

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        binding.employees.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {

                Log.e("mallal", "dds " + mListEmployees[position].employeeName)
                //    Log.e("mallal","dds "+ mListEmployees[position].employeeId)
                mListEmployees[position].employeeId?.let {
                    employeeId = it
                }
                // employeeId= mListEmployees[position].employeeId!!
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }


    private fun setObserver() {
        userViewModel.allRolesResponseList.observe(this) {

            mListRoles.add(GetAllRolesResponseData(roleName = "Select Role", roleId = 0))
            mListRoles.addAll(it)

            rolesSpinner = RolesSpinnerAdapter(mListRoles)
            binding.roles.adapter = rolesSpinner

            try {
                if (from == "edit") {
                    for (i in 0 until mListRoles.size) {

                        if (mListRoles[i].roleId == roleId) {
                            binding.roles.setSelection(i)
                        }
                    }

                }
            } catch (_: Exception) {

            }

        }

        userViewModel.allEmployeesResponseList.observe(this) {
            //mListEmployees = it
            mListEmployees.add(EmployeeListResponse(employeeName = "Select Employee"))
            mListEmployees.addAll(it)
            Log.e("sajkjdss ", " " + mListEmployees.size)
            employeeSpinner = EmployeeSpinnerAdapter(mListEmployees)
            binding.employees.adapter = employeeSpinner

            try {
                if (from == "edit") {
                    for (i in 0 until mListEmployees.size) {

                        if (mListEmployees[i].employeeId == employeeId) {
                            binding.employees.setSelection(i)
                        }
                    }

                }
            } catch (_: Exception) {

            }

        }

        userViewModel.addLocaResponse.observe(this) {

            CommonMethods.showToast(this, "Added Successfully")
            val intent = Intent()
            setResult(121, intent)
            finish()

        }


        userViewModel.updateUserResponse.observe(this) {

            CommonMethods.showToast(this, "Updated successfully")
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