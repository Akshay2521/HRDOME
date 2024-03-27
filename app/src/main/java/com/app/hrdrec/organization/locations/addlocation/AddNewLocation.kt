package com.app.hrdrec.organization.locations.addlocation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.hrdrec.R
import com.app.hrdrec.Url
import com.app.hrdrec.databinding.ActivityAddNewLocationBinding
import com.app.hrdrec.organization.locations.get_location_response.AllLocationData
import com.app.hrdrec.organization.locations.LocationViewModel
import com.app.hrdrec.organization.locations.PatientRelationSpinnerAdapter
import com.app.hrdrec.organization.locations.StateSpinnerAdapter
import com.app.hrdrec.organization.locations.get_location_response.CountryListModel
import com.app.hrdrec.organization.locations.state_response.StateDTOs
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddNewLocation : AppCompatActivity(), CommonInterface {

    private val locationViewModel: LocationViewModel by viewModels()

    private val binding by lazy { ActivityAddNewLocationBinding.inflate(layoutInflater) }

    var countryList = arrayListOf<CountryListModel>()
    private lateinit var patientRelationAdapter: PatientRelationSpinnerAdapter

    var countryId = "0"
    var stateId = "0"
    var from: String = "add"
    private var locationId: Int = 0

    var stateList = arrayListOf<StateDTOs>()
    private lateinit var stateSpiner: StateSpinnerAdapter

    var isHeadOfficeSelected:Boolean=false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_add_new_location)

        setContentView(binding.root)
        locationViewModel.setCallBacks(this)
        patientRelationAdapter = PatientRelationSpinnerAdapter(countryList)
        binding.CountrySpinner.adapter = patientRelationAdapter

        stateSpiner = StateSpinnerAdapter(stateList)
        binding.sateSpinner.adapter = stateSpiner
        stateList.add(StateDTOs(name = "Select State", id = 0))

        stateSpiner.updateList(stateList)
        stateSpiner.notifyDataSetChanged()

        setObserver()

        from = intent.getStringExtra("from")!!

        if (from == "add") {
            binding.saveloc.text = "Save"
        } else {
            binding.saveloc.text = "Update"
            val mObj = intent.getSerializableExtra("mObj") as AllLocationData

            binding.apply {
                locName.setText(mObj.locationName)
                ad1.setText(mObj.address1)
                ad2.setText(mObj.address2)
                locCity.setText(mObj.city)
                locPin.setText(mObj.zipCode)

                countryId = mObj.countryId.toString()
                stateId = mObj.stateId.toString()

                locationId = mObj.id!!
                isHeadOfficeSelected= mObj.headOffice!!
                isHeadOfficeUpdate(isHeadOfficeSelected)
            }


        }
        locationViewModel.getCountry()
        binding.CountrySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {

                Log.e("Id", "aaad" + countryList[position].id)
                if (countryList[position].name != "Select Country") {
                    countryList[position].id?.let { getStateList(it) }
                    countryId = countryList[position].id.toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        binding.sateSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {

                Log.e("stateList", "dds " + stateList[position].name)
                if (stateList[position].name != "Select State")
                    stateId = stateList[position].id.toString()
                // countryList[position].id?.let { getStateList(it) }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }







        binding.apply {


            saveloc.setOnClickListener {

                if (locName.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewLocation, "Please enter location name")
                    return@setOnClickListener
                }

                if (ad1.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewLocation, "Please enter address")
                    return@setOnClickListener
                }

                if (countryId == "0") {
                    CommonMethods.showToast(this@AddNewLocation, "Please select country")
                    return@setOnClickListener
                }

                if (stateId == "0") {
                    CommonMethods.showToast(this@AddNewLocation, "Please select state")
                    return@setOnClickListener
                }
                if (locCity.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewLocation, "Please enter city")
                    return@setOnClickListener
                }

                if (locPin.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewLocation, "Please enter pincode")
                    return@setOnClickListener
                }
                if (from == "add") {
                    val params = AddLocationParams(
                        name = locName.text.toString(),
                        address1 = ad1.text.toString(),
                        address2 = ad2.text.toString(),
                        city = locCity.text.toString(),
                        zipCode = locPin.text.toString(),
                        countryId = countryId,
                        stateId = stateId,
                        organizationId = locationViewModel.sharedPreferences.getInt(Url.ORGANISATIONID),
                        headOffice = isHeadOfficeSelected

                    )

                    locationViewModel.addAddress(params)
                } else {
                    val params = UpdateLocationParams(
                        name = locName.text.toString(),
                        address1 = ad1.text.toString(),
                        address2 = ad2.text.toString(),
                        city = locCity.text.toString(),
                        zipCode = locPin.text.toString(),
                        countryId = countryId,
                        stateId = stateId,
                        organizationId = locationViewModel.sharedPreferences.getInt(Url.ORGANISATIONID),
                        id = locationId,
                        headOffice = isHeadOfficeSelected
                    )
                    locationViewModel.updateAddress(params)
                }
            }

            imgSelected.setOnClickListener {
                if(isHeadOfficeSelected)
                {
                    isHeadOfficeSelected=false
                    isHeadOfficeUpdate(isHeadOfficeSelected)
               }
                else{
                    isHeadOfficeSelected=true
                    isHeadOfficeUpdate(isHeadOfficeSelected)
                }


            }
        }
    }
    fun isHeadOfficeUpdate(value :Boolean){
        if (value) {
            binding.imgSelected.setImageResource(R.drawable.ic_radio_button_checked)
        } else {
            binding.imgSelected.setImageResource(R.drawable.ic_radio_button_unchecked)
        }
    }

    private fun getStateList(isdCode: Int) {
        locationViewModel.getStateById(isdCode)
    }

    private fun setObserver() {

        locationViewModel.countryList.observe(this) {
            countryList.add(CountryListModel(name = "Select Country", id = 0))
            countryList.addAll(it)
            //   countryList=it

            patientRelationAdapter.updateList(countryList)
            patientRelationAdapter.notifyDataSetChanged()

            if (from == "edit") {
                for (i in 0 until countryList.size) {
                    Log.e("sdjdknfdn", "country " + countryList[i].id)
                    if (countryList[i].id == countryId.toInt()) {
                        binding.CountrySpinner.setSelection(i)
                    }
                }
            }
        }

        locationViewModel.stateList.observe(this) {
            stateList.clear()
            stateList.add(StateDTOs(name = "Select State", id = 0))
            //stateList.addAll()
            stateList.addAll(it)
            //     stateList=it
            stateSpiner.updateList(stateList)
            stateSpiner.notifyDataSetChanged()
            Log.e("sdsadads", "dsds " + stateList.size)

            if (from == "edit") {
                for (i in 0 until stateList.size) {
                    Log.e("state", "asa " + stateList[i].id)
                    if (stateList[i].id == stateId.toInt()) {
                        binding.sateSpinner.setSelection(i)
                    }
                }
            }
        }
        locationViewModel.addLocaResponse.observe(this) {
            CommonMethods.showToast(this, "Added successfully")
            val intent = Intent()
            setResult(121, intent)
            finish()
        }

        locationViewModel.updateLocResponse.observe(this) {
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