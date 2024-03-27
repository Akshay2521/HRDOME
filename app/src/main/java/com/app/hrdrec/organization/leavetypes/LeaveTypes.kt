package com.app.hrdrec.organization.leavetypes

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.app.hrdrec.R
import com.app.hrdrec.databinding.ActivityLeaveTypesBinding
import com.app.hrdrec.organization.leavetypes.leavetype_models.LeavetypeData
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LeaveTypes : AppCompatActivity(), CommonInterface {

    var id: Int = 1
    private val binding by lazy { ActivityLeaveTypesBinding.inflate(layoutInflater) }

    @Inject
    lateinit var albumDataAdapter: LeavetypeDataAdapter
    var mList = arrayListOf<LeavetypeData>()

    private val leavetypeViewModel: LeavetypeViewModel by viewModels()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_users)
        setContentView(binding.root)
        leavetypeViewModel.setCallBacks(this)
        // id=intent.getIntExtra("id",1)
        binding.recyclerView.adapter = albumDataAdapter
        setObserver()
        leavetypeViewModel.getLeavetypeRoles()

        val assignLeavetypes = findViewById<FloatingActionButton>(R.id.assignLeavetype)
        assignLeavetypes.setOnClickListener {
            val intent = Intent(this, AssignLeavetypes::class.java)
            intent.putExtra("from", "add")
            resultLauncher.launch(intent)
            // startActivity(intent)
        }

        albumDataAdapter.setItemClick(object : ClickInterfaceLeavetype<LeavetypeData> {
            override fun onClick(data: LeavetypeData) {
                val intent = Intent(this@LeaveTypes, AssignLeavetypes::class.java)
                intent.putExtra("from", "edit")
                intent.putExtra("mObj", data)
                resultLauncher.launch(intent)

            }

            override fun onClickDelete(data: LeavetypeData) {
                CommonMethods.showAlertYesNoMessage(
                    this@LeaveTypes, "Are you sure you want to delete this record"
                ) {

                    data.locationId?.let { leavetypeViewModel.deleteLeavetype(it) }
                }
            }

        })



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

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.e("LeaveType", "Leave type fetched" + result.resultCode)
            if (result.resultCode == 121) {
                Log.e("LeaveType", "success")
                try {
                    leavetypeViewModel.getLeavetypeRoles()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }else{
                Log.e("LeaveType", "Leave type not fetched" + result.resultCode)
            }
        }

    fun filtered(text: String?) {

        Log.e("Check", "" + text)
        val query = text?.lowercase()
        val temp: ArrayList<LeavetypeData> = ArrayList()


        mList.forEach { d ->
            if (d.name!!.lowercase().contains(query!!)) {
                temp.add(d)
            }
        }
        // if (temp.size == 0)
        albumDataAdapter.updateList(temp)

    }

    private fun setObserver() {
        leavetypeViewModel.myResponseList.observe(this) {
            Log.e("data", "xccc ")
            mList = it
            albumDataAdapter.updateAlbumData(it)
//            // it.data.let { it1 -> Log.d("main", it1.toString()) }
//            recyclerView.visibility = View.VISIBLE
        }

        leavetypeViewModel.addLocaResponse.observe(this) {

            CommonMethods.showToast(this, "Deleted Successfully")
            leavetypeViewModel.getLeavetypeRoles()
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