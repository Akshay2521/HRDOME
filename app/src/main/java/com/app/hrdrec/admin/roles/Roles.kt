package com.app.hrdrec.admin.roles

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.app.hrdrec.admin.roles.get_all_roles_response.ClickInterfaceOrgan
import com.app.hrdrec.admin.roles.get_all_roles_response.RolesDataAdapter
import com.app.hrdrec.databinding.ActivityRolesBinding
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import com.app.hrdrec.admin.roles.get_all_roles_response.GetAllRolesResponseData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Roles : AppCompatActivity(), CommonInterface {

    var id: Int = 1

    private val binding by lazy { ActivityRolesBinding.inflate(layoutInflater) }

    @Inject
    lateinit var albumDataAdapter: RolesDataAdapter

    var mList = arrayListOf<GetAllRolesResponseData>()

    private val rolesViewModel: RoleViewModel by viewModels()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        id = intent.getIntExtra("id", 1)
        // val addRoles = findViewById<FloatingActionButton>(R.id.addRole)
        binding.recyclerView.adapter = albumDataAdapter
        rolesViewModel.setCallBacks(this)
        setObserver()
        rolesViewModel.getAllRoles()

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (s.isEmpty()) {
                    filtered(s.toString())
                } else {
                    filtered(s.toString())
                }
            }
        })



        binding.addRole.setOnClickListener {
            val intent = Intent(this, AddRoles::class.java)
            intent.putExtra("from", "add")
            resultLauncher.launch(intent)
            // startActivity(intent)
        }

        albumDataAdapter.setItemClick(object : ClickInterfaceOrgan<GetAllRolesResponseData> {
            override fun onClick(data: GetAllRolesResponseData) {
                val intent = Intent(this@Roles, AddRoles::class.java)
                intent.putExtra("from", "edit")
                intent.putExtra("mObj", data)
                resultLauncher.launch(intent)

            }

            override fun onClickDelete(data: GetAllRolesResponseData) {
                CommonMethods.showAlertYesNoMessage(
                    this@Roles, "Are you sure you want to delete this record"
                ) {

                    data.roleId?.let { rolesViewModel.deleteRoles(it) }
                }
            }

        })

    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == 121) {
                try {
                    rolesViewModel.getAllRoles()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

            }
        }

    fun filtered(text: String?) {

        Log.e("Check", "" + text)
        val query = text?.lowercase()
        val temp: ArrayList<GetAllRolesResponseData> = ArrayList()


        mList.forEach { d ->
            if (d.roleName != null) {
                if (d.roleName!!.lowercase().contains(query!!)) {
                    temp.add(d)
                }
            }
        }
        // if (temp.size == 0)
        albumDataAdapter.updateList(temp)

    }

    private fun setObserver() {
        rolesViewModel.myResponseList.observe(this) {
            mList = it
            albumDataAdapter.updateAlbumData(it)
//            // it.data.let { it1 -> Log.d("main", it1.toString()) }
//            recyclerView.visibility = View.VISIBLE
        }


        rolesViewModel.addLocaResponse.observe(this) {

            CommonMethods.showToast(this, "Deleted Successfully")
            rolesViewModel.getAllRoles()
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