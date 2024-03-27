package com.app.hrdrec.admin.users

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
import com.app.hrdrec.R
import com.app.hrdrec.admin.users.user_models.UserData
import com.app.hrdrec.databinding.ActivityUsersBinding
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Users : AppCompatActivity(), CommonInterface {

    var id:Int=1
    private val binding by lazy { ActivityUsersBinding.inflate(layoutInflater) }

    @Inject
    lateinit var albumDataAdapter: UserDataAdapter
    var mList = arrayListOf<UserData>()

    private val userViewModel: UserViewModel by viewModels()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_users)
        setContentView(binding.root)
        userViewModel.setCallBacks(this)
       // id=intent.getIntExtra("id",1)
        binding.recyclerView.adapter = albumDataAdapter
        setObserver()
        userViewModel.getUserRoles()

        val assignUsers = findViewById<FloatingActionButton>(R.id.assignUser)
        assignUsers.setOnClickListener {
            val intent = Intent(this, AssignUsers::class.java)
            intent.putExtra("from","add")
            resultLauncher.launch(intent)
           // startActivity(intent)
        }

        albumDataAdapter.setItemClick(object : ClickInterfaceUser<UserData> {
            override fun onClick(data: UserData) {
                val intent = Intent(this@Users, AssignUsers::class.java)
                intent.putExtra("from","edit")
                intent.putExtra("mObj",data)
                resultLauncher.launch(intent)

            }

            override fun onClickDelete(data: UserData) {
                CommonMethods.showAlertYesNoMessage(this@Users,"Are you sure you want to delete this record"){

                    data.roleId?.let { userViewModel.deleteUser(it) }
                }
            }

        } )



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
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        if (result.resultCode == 121) {
            try {
                userViewModel.getUserRoles()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }
    }

    fun filtered(text: String?) {

        Log.e("Check",""+ text)
        val query = text?.lowercase()
        val temp: ArrayList<UserData> = ArrayList()


        mList.forEach { d ->
            if (d.username!!.lowercase().contains(query!!)) {
                temp.add(d)
            }
        }
        // if (temp.size == 0)
        albumDataAdapter.updateList(temp)

    }

    private fun setObserver() {
        userViewModel.myResponseList.observe(this) {
            Log.e("data", "xccc ")
            mList = it
            albumDataAdapter.updateAlbumData(it)
//            // it.data.let { it1 -> Log.d("main", it1.toString()) }
//            recyclerView.visibility = View.VISIBLE
        }

        userViewModel.addLocaResponse.observe(this) {

            CommonMethods.showToast(this, "Deleted Successfully")
            userViewModel.getUserRoles()
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
