package com.app.hrdrec.organization.leavetypes

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import com.app.hrdrec.Url
import com.app.hrdrec.databinding.ActivityAssignLeavetypesBinding
import com.app.hrdrec.organization.leavetypes.leavetype_models.LeavetypeData
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class AssignLeavetypes : AppCompatActivity(), CommonInterface {
    private val binding by lazy { ActivityAssignLeavetypesBinding.inflate(layoutInflater) }

    private val leavetypeViewModel: LeavetypeViewModel by viewModels()

    var mListLocations = arrayListOf<com.app.hrdrec.organization.leavetypes.locations_response.LocationListResponse>()

    private lateinit var locationSpinner: LocationSpinnerAdapter

    var locationId: Int = 0
    var id: Int = 0
    var from = "add"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        leavetypeViewModel.setCallBacks(this)
        from = intent.getStringExtra("from")!!
        if (from == "add") {
            binding.btnSaveLeavetype.text = "Save"
        } else {
            binding.btnSaveLeavetype.text = "Update"

            val mObj = intent.getSerializableExtra("mObj") as LeavetypeData

            binding.apply {
                edtPurpose.setText(mObj.specialTypeEnum)
                edtName.setText(mObj.name)
                edtFrequency.setText(mObj.frequency)
                edtCreditingMethod.setText(mObj.automate)
                edtApplicableGender.setText(mObj.genderEnum)
                mObj.numberOfDays?.let { edtNumberofLeaves }
                edtCarryforward.setText(mObj.carryforward)
                mObj.maxcarryforward?.let { edtMaximumCarryforward }
                try {
                    id = mObj.id!!
                    locationId = mObj.locationId!!

                } catch (_: Exception) {

                }
            }
        }


        setObserver()
        leavetypeViewModel.getAllLocations()

        binding.btnSaveLeavetype.setOnClickListener {

            if (binding.edtPurpose.text.toString().isEmpty()) {
                CommonMethods.showToast(this, "Please enter Purpose")
                return@setOnClickListener
            }

            if (binding.edtName.text.toString().isEmpty()) {
                CommonMethods.showToast(this, "Please enter Name")
                return@setOnClickListener
            }

            if (binding.edtFrequency.text.toString().isEmpty()) {
                CommonMethods.showToast(this, "Please enter Frequency")
                return@setOnClickListener
            }

            if (binding.edtCreditingMethod.text.toString().isEmpty()) {
                CommonMethods.showToast(this, "Please enter Crediting Method")
                return@setOnClickListener
            }

            if (binding.edtApplicableGender.text.toString().isEmpty()) {
                CommonMethods.showToast(this, "Please enter Applicable Gender")
                return@setOnClickListener
            }

            if (binding.edtNumberofLeaves.text.toString().isEmpty()) {
                CommonMethods.showToast(this, "Please enter Number of Leaves")
                return@setOnClickListener
            }

            if (binding.edtCarryforward.text.toString().isEmpty()) {
                CommonMethods.showToast(this, "Please enter Carryforward")
                return@setOnClickListener
            }

            if (binding.edtMaximumCarryforward.text.toString().isEmpty()) {
                CommonMethods.showToast(this, "Please enter Maximum Carryforward")
                return@setOnClickListener
            }

            if (from == "add") {
                val mObj = AddLeavetypePayload(
                    binding.edtPurpose.text.toString(),
                    binding.edtName.text.toString(),
                    binding.edtFrequency.text.toString(),
                    binding.edtCreditingMethod.text.toString(),
                    binding.edtApplicableGender.text.toString(),
                    binding.edtNumberofLeaves,
                    binding.edtCarryforward.text.toString(),
                    binding.edtMaximumCarryforward,
                    locationId,
                    leavetypeViewModel.sharedPreferences.getInt(
                        Url.ORGANISATIONID
                    )
                )

                leavetypeViewModel.addLeavetype(mObj)
            } else {
                val mObj = UpdateLeavetypePayload(
                    binding.edtPurpose.text.toString(),
                    binding.edtName.text.toString(),
                    binding.edtFrequency.text.toString(),
                    binding.edtCreditingMethod.text.toString(),
                    binding.edtApplicableGender.text.toString(),
                    binding.edtNumberofLeaves,
                    binding.edtCarryforward.text.toString(),
                    binding.edtMaximumCarryforward,
                    locationId,
                    leavetypeViewModel.sharedPreferences.getInt(
                        Url.ORGANISATIONID
                    ),
                    id

                )
                leavetypeViewModel.UpdateLeavetype(mObj)
            }
        }

        binding.Location.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {

                Log.e("mallal", "dds " + mListLocations[position].locationName)
                //    Log.e("mallal","dds "+ mListEmployees[position].employeeId)
                mListLocations[position].id?.let {
                    locationId = it
                }
                // employeeId= mListEmployees[position].employeeId!!
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }


    private fun setObserver() {

        leavetypeViewModel.allLocationsResponseList.observe(this) {
            //mListEmployees = it
            mListLocations.add(com.app.hrdrec.organization.leavetypes.locations_response.LocationListResponse(locationName = "Select Location"))
            mListLocations.addAll(it)
            Log.e("sajkjdss ", " " + mListLocations.size)
            locationSpinner = LocationSpinnerAdapter(mListLocations)
            binding.Location.adapter = locationSpinner

            try {
                if (from == "edit") {
                    for (i in 0 until mListLocations.size) {

                        if (mListLocations[i].id == locationId) {
                            binding.Location.setSelection(i)
                        }
                    }

                }
            } catch (_: Exception) {

            }

        }

        leavetypeViewModel.addLocaResponse.observe(this) {

            CommonMethods.showToast(this, "Added Successfully")
            val intent = Intent()
            setResult(121, intent)
            finish()

        }


        leavetypeViewModel.updateLeavetypeResponse.observe(this) {

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