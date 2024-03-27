package com.app.hrdrec.organization.organizationprofile.addorganizationprofile

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.app.hrdrec.Url
import com.app.hrdrec.databinding.ActivityAddNewOrganizationProfileBinding
import com.app.hrdrec.organization.organizationprofile.OrganizationProfileViewModel
import com.app.hrdrec.organization.organizationprofile.get_organizationprofile_response.AllOrganizationProfileData
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewOrganizationProfile : AppCompatActivity(), CommonInterface {
    private val organizationProfileViewModel: OrganizationProfileViewModel by viewModels()

    private val binding by lazy { ActivityAddNewOrganizationProfileBinding.inflate(layoutInflater) }


    private var from: String = "add"
    private var organizationProfileId: Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_add_new_location)

        setContentView(binding.root)
        organizationProfileViewModel.setCallBacks(this)
        setObserver()

        from = intent.getStringExtra("from")!!

        if (from == "add") {
            binding.saveOrganizationProfile.text = "Save"
        } else {
            binding.saveOrganizationProfile.text = "Update"
            val mObj = intent.getSerializableExtra("mObj") as AllOrganizationProfileData

            binding.apply {
                OrganizationName.setText(mObj.name)
                Website.setText(mObj.website)
                FoundationDate.setText(mObj.foundationDate)
                mObj.gratitudePeriod?.let { GratitudePeriod.setText(it) }
                mObj.noOfDaysForPasswordExpiry?.let { PasswordExpiryDays.setText(it) }
                mObj.maxNoOfAllowedLoginAttempts?.let { MaxInvalidLoginAttempts.setText(it) }
//                Logo.setText(mObj.logo)
                LocationName.setText(mObj.locationName)
                Country.setText(mObj.countryName)
                Address1.setText(mObj.address1)
                Address2.setText(mObj.address2)
                City.setText(mObj.city)
                State.setText(mObj.stateName)
                ZipCode.setText(mObj.zipCode)
                FirstName.setText(mObj.firstName)
                LastName.setText(mObj.lastName)
                Username.setText(mObj.username)
                Email.setText(mObj.email)
                AlternateEmail.setText(mObj.alternateEmail)
                PhoneNumber.setText(mObj.phoneNumber)
                AlternatePhoneNumber.setText(mObj.alternatePhoneNumber)
                organizationProfileId = mObj.id!!
            }
        }
        binding.apply {
            saveOrganizationProfile.setOnClickListener {

                if (OrganizationName.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewOrganizationProfile, "Please enter OrganizationName")
                    return@setOnClickListener
                }

                if (Website.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewOrganizationProfile, "Please enter Website")
                    return@setOnClickListener
                }

                if (FoundationDate.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewOrganizationProfile, "Please enter FoundationDate")
                    return@setOnClickListener
                }

                if (GratitudePeriod.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewOrganizationProfile, "Please enter GratitudePeriod")
                    return@setOnClickListener
                }

                if (PasswordExpiryDays.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewOrganizationProfile, "Please enter PasswordExpiryDays")
                    return@setOnClickListener
                }

                if (MaxInvalidLoginAttempts.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewOrganizationProfile, "Please enter MaxInvalidLoginAttempts")
                    return@setOnClickListener
                }

//                if (Logo.text.toString().isEmpty()) {
//                    CommonMethods.showToast(this@AddNewOrganizationProfile, "Please enter Logo")
//                    return@setOnClickListener
//                }

                if (LocationName.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewOrganizationProfile, "Please enter LocationName")
                    return@setOnClickListener
                }

                if (Country.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewOrganizationProfile, "Please enter Country")
                    return@setOnClickListener
                }

                if (Address1.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewOrganizationProfile, "Please enter Address1")
                    return@setOnClickListener
                }

                if (Address2.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewOrganizationProfile, "Please enter Address2")
                    return@setOnClickListener
                }

                if (City.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewOrganizationProfile, "Please enter City")
                    return@setOnClickListener
                }

                if (State.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewOrganizationProfile, "Please enter State")
                    return@setOnClickListener
                }

                if (ZipCode.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewOrganizationProfile, "Please enter ZipCode")
                    return@setOnClickListener
                }

                if (FirstName.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewOrganizationProfile, "Please enter FirstName")
                    return@setOnClickListener
                }

                if (LastName.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewOrganizationProfile, "Please enter LastName")
                    return@setOnClickListener
                }

                if (Username.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewOrganizationProfile, "Please enter Username")
                    return@setOnClickListener
                }

                if (Email.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewOrganizationProfile, "Please enter Email")
                    return@setOnClickListener
                }

                if (AlternateEmail.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewOrganizationProfile, "Please enter AlternateEmail")
                    return@setOnClickListener
                }

                if (PhoneNumber.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewOrganizationProfile, "Please enter PhoneNumber")
                    return@setOnClickListener
                }

                if (AlternatePhoneNumber.text.toString().isEmpty()) {
                    CommonMethods.showToast(this@AddNewOrganizationProfile, "Please enter AlternatePhoneNumber")
                    return@setOnClickListener
                }


                if (from == "add") {
                    val params = AddOrganizationProfileParams(
                        name = OrganizationName.text.toString(),
                        website = Website.text.toString(),
                        foundationDate = FoundationDate.text.toString(),
                        gratitudePeriod = GratitudePeriod,
                        noOfDaysForPasswordExpiry = PasswordExpiryDays,
                        maxNoOfAllowedLoginAttempts = MaxInvalidLoginAttempts,
//                        logo = Logo.text.toString(),
                        locationName = LocationName.text.toString(),
                        countryName = Country.text.toString(),
                        address1 = Address1.text.toString(),
                        address2 = Address2.text.toString(),
                        city = City.text.toString(),
                        stateId = State.text.toString(),
                        zipCode = ZipCode.text.toString(),
                        firstName = FirstName.text.toString(),
                        lastName = LastName.text.toString(),
                        username = Username.text.toString(),
                        email = Email.text.toString(),
                        alternateEmail = AlternateEmail.text.toString(),
                        phoneNumber = PhoneNumber.text.toString(),
                        alternatePhoneNumber = AlternatePhoneNumber.text.toString(),

                        organizationId = organizationProfileViewModel.sharedPreferences.getInt(Url.ORGANISATIONID),

                        )

                    organizationProfileViewModel.addAddress(params)
                } else {
                    val params = UpdateOrganizationProfileParams(
                        name = OrganizationName.text.toString(),
                        website = Website.text.toString(),
                        foundationDate = FoundationDate.text.toString(),
                        gratitudePeriod = GratitudePeriod,
                        noOfDaysForPasswordExpiry = PasswordExpiryDays,
                        maxNoOfAllowedLoginAttempts = MaxInvalidLoginAttempts,
//                        logo = Logo.text.toString(),
                        locationName = LocationName.text.toString(),
                        countryName = Country.text.toString(),
                        address1 = Address1.text.toString(),
                        address2 = Address2.text.toString(),
                        city = City.text.toString(),
                        stateId = State.text.toString(),
                        zipCode = ZipCode.text.toString(),
                        firstName = FirstName.text.toString(),
                        lastName = LastName.text.toString(),
                        username = Username.text.toString(),
                        email = Email.text.toString(),
                        alternateEmail = AlternateEmail.text.toString(),
                        phoneNumber = PhoneNumber.text.toString(),
                        alternatePhoneNumber = AlternatePhoneNumber.text.toString(),
                        organizationId = organizationProfileViewModel.sharedPreferences.getInt(Url.ORGANISATIONID),
                        id = organizationProfileId
                    )
                    organizationProfileViewModel.updateAddress(params)
                }
            }
        }
    }
    private fun setObserver() {

        organizationProfileViewModel.addOrganizationProfileResponse.observe(this) {
            CommonMethods.showToast(this, "Added successfully")
            val intent = Intent()
            setResult(121, intent)
            finish()
        }

        organizationProfileViewModel.updateorganizationProfileResponse.observe(this) {
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