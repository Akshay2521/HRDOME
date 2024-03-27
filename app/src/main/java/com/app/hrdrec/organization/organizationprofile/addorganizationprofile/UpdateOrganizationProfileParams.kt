package com.app.hrdrec.organization.organizationprofile.addorganizationprofile

import android.widget.EditText

data class UpdateOrganizationProfileParams(

    val name: String,
    val website: String,
    val foundationDate: String,
    val gratitudePeriod: EditText,
    val noOfDaysForPasswordExpiry: EditText,
    val maxNoOfAllowedLoginAttempts: EditText,
//    val logo: String,
    val locationName: String,
    val countryName: String,
    val address1: String,
    val address2: String,
    val city: String,
    val stateId: String,
    val zipCode: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val alternateEmail: String,
    val phoneNumber: String,
    val alternatePhoneNumber: String,
    val organizationId: Int,
    val id: Int

)