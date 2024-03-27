package com.app.hrdrec.organization.organizationprofile

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.R
import com.app.hrdrec.databinding.ActivityOrganizationProfileBinding
import com.app.hrdrec.organization.organizationprofile.addorganizationprofile.AddNewOrganizationProfile
import com.app.hrdrec.organization.organizationprofile.get_organizationprofile_response.AllOrganizationProfileData
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrganizationProfile : AppCompatActivity(), CommonInterface {
    private val binding by lazy { ActivityOrganizationProfileBinding.inflate(layoutInflater) }
    var id: Int = 1
    private val organizationProfileViewModel: OrganizationProfileViewModel by viewModels()
    var mList = arrayListOf<AllOrganizationProfileData>()

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //  setContentView(R.layout.activity_location)
        id = intent.getIntExtra("id", 1)
        organizationProfileViewModel.setCallBacks(this)

        val addNewOrganizationProfile =
            findViewById<FloatingActionButton>(R.id.createNewOrganizationProfile)
        addNewOrganizationProfile.setOnClickListener {
            val intent = Intent(this, AddNewOrganizationProfile::class.java)
            intent.putExtra("from", "add")
            //startActivity(intent)
            resultLauncher.launch(intent)
        }

//        initRecyclerView()


        organizationProfileViewModel.getAllOrganizationProfile()

        organizationProfileViewModel.myResponseList.observe(this) {
//            mList = it
//            postAdapter.setOrganizationProfileData(mList)
//            binding.recyclerView.visibility = View.VISIBLE
            Log.e("OrgProfile", "Checking$it")
            val organizationProfile = it
            binding.textViewOrganizationName.text =
                " OrganizationName : ${organizationProfile.name}"
//            binding.website.text = " Website : ${organizationProfile.website}"
//            binding.foundationDate.text = " FoundationDate : ${organizationProfile.foundationDate}"
//            holder.gratitudePeriod.text = " gratitudePeriod : ${organizationProfile.gratitudePeriod}"
//            holder.passwordExpiryDays.text = " PasswordExpiryDays : ${organizationProfile.noOfDaysForPasswordExpiry}"
//            holder.maxInvalidLoginAttempts.text = " maxInvalidLoginAttempts : ${organizationProfile.maxNoOfAllowedLoginAttempts}"
//            holder.logo.text = " logo : ${OrganizationProfile.logo}"
//            holder.locationName.text = " locationName : ${organizationProfile.locationName}"
//            holder.country.text = "country : ${organizationProfile.countryName}"
//            holder.address1.text = " address1 : ${organizationProfile.address1}"
//            holder.address2.text = "address2 : ${organizationProfile.address2}"
//            holder.city.text = "city : ${organizationProfile.city}"
//            holder.state.text = " state : ${organizationProfile.stateName}"
//            holder.zipCode.text = "zipCode : ${organizationProfile.zipCode}"
//            holder.firstName.text = "firstName : ${organizationProfile.firstName}"
//            holder.lastName.text = "lastName : ${organizationProfile.lastName}"
//            holder.username.text = "username : ${organizationProfile.username}"
//            holder.email.text = "email : ${organizationProfile.email}"
//            holder.alternateEmail.text = "alternateEmail : ${organizationProfile.alternateEmail}"
//            holder.phoneNumber.text = "phoneNumber : ${organizationProfile.phoneNumber}"
//            holder.alternatePhoneNumber.text = "alternatePhoneNumber : ${organizationProfile.alternatePhoneNumber}"
//
//            holder.imgDelete.setOnClickListener {
//                callBack(organizationProfile, "delete")
//            }
//
//            holder.imgEdit.setOnClickListener {
//                callBack(organizationProfile, "edit")
//            }
        }

        organizationProfileViewModel.addOrganizationProfileResponse.observe(this) {
            CommonMethods.showToast(this, "Deleted Successfully")
            organizationProfileViewModel.getAllOrganizationProfile()
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    filtered(s.toString())
                } else {
                    filtered(s.toString())
                }
            }
        })
    }

    fun filtered(text: String?) {
        val query = text?.lowercase()
        val temp: ArrayList<AllOrganizationProfileData> = ArrayList()
        mList.forEach { d ->

            if (d.name != null) {
                if (d.name!!.lowercase().contains(query!!)) {
                    temp.add(d)
                }
            }
        }
//        postAdapter.setOrganizationProfileData(temp)

    }


//    private fun initRecyclerView() {
//        postAdapter = OrganizationProfileAdapter(mList) { mObj, from ->
//
//            if (from == "delete") {
//                CommonMethods.showAlertYesNoMessage(
//                    this,
//                    "Are you sure you want to delete this record"
//                ) {
//
////                    organizationProfileViewModel.deleteAddress(mObj.id!!)
//                }
//            } else {
//                val intent = Intent(this, AddNewOrganizationProfile::class.java)
//                intent.putExtra("from", "edit")
//                intent.putExtra("mObj", mObj)
//                resultLauncher.launch(intent)
//            }
//
//
//        }
////        binding.recyclerView.apply {
////            setHasFixedSize(true)
////            layoutManager = LinearLayoutManager(this@OrganizationProfile)
////            adapter = postAdapter
////        }
//    }


    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            Log.e("Organization Profile", "Organization Profile" + result.resultCode)

            if (result.resultCode == 121) {
                Log.e("Organization Profile", "Organization Profile")
                try {
                    organizationProfileViewModel.getAllOrganizationProfile()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

            } else {
                Log.e("Organization Profile", "Organization Profile")
            }
        }

    override fun onResponseSuccess() {

    }

    override fun noListData() {
        mList.clear()
//        postAdapter.notifyDataSetChanged()
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