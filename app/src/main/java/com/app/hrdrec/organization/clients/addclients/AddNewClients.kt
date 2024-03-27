package com.app.hrdrec.organization.clients.addclients

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.hrdrec.Url
import com.app.hrdrec.databinding.ActivityAddNewClientsBinding
import com.app.hrdrec.organization.clients.ClientsViewModel
import com.app.hrdrec.organization.clients.get_clients_response.AllClientsData
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewClients : AppCompatActivity(), CommonInterface {
    private val clientsViewModel: ClientsViewModel by viewModels()

    private val binding by lazy { ActivityAddNewClientsBinding.inflate(layoutInflater) }


    private var from: String = "add"
    private var clientsId: Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_add_new_location)

        setContentView(binding.root)
        clientsViewModel.setCallBacks(this)
        setObserver()

        from = intent.getStringExtra("from")!!

        if (from == "add") {
            binding.saveclients.text = "Save"
        } else {
            binding.saveclients.text = "Update"
            val mObj = intent.getSerializableExtra("mObj") as AllClientsData

            binding.apply {
                Name.setText(mObj.name)
                Email.setText(mObj.email)
                LocationName.setText(mObj.locationName)

                clientsId = mObj.id!!
            }
        }
        binding.apply {
            saveclients.setOnClickListener {

                if (Name.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewClients, "Please enter location name")
                    return@setOnClickListener
                }

                if (Email.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewClients, "Please enter Email")
                    return@setOnClickListener
                }

                if (LocationName.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewClients, "Please enter LocationName")
                    return@setOnClickListener
                }

                if (from == "add") {
                    val params = AddClientsParams(
                        name = Name.text.toString(),
                        email = Email.text.toString(),
                        locationName = LocationName.text.toString(),
                        organizationId = clientsViewModel.sharedPreferences.getInt(Url.ORGANISATIONID),

                        )

                    clientsViewModel.addAddress(params)
                } else {
                    val params = UpdateClientsParams(
                        name = Name.text.toString(),
                        email = Email.text.toString(),
                        locationName = LocationName.text.toString(),
                        organizationId = clientsViewModel.sharedPreferences.getInt(Url.ORGANISATIONID),
                        id = clientsId
                    )
                    clientsViewModel.updateAddress(params)
                }
            }
        }
    }
    private fun setObserver() {

        clientsViewModel.addClientsResponse.observe(this) {
            CommonMethods.showToast(this, "Added successfully")
            val intent = Intent()
            setResult(121, intent)
            finish()
        }

        clientsViewModel.updateClientsResponse.observe(this) {
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