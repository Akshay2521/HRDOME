package com.app.hrdrec.organization.organizationprofile.get_organizationprofile_response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AllOrganizationProfileData(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("website") var website: String? = null,
    @SerializedName("gratitudePeriod") var gratitudePeriod: Int? = null,
    @SerializedName("foundationDate") var foundationDate: String? = null,
    @SerializedName("deleted") var deleted: Boolean? = null,
    @SerializedName("packagePlan") var packagePlan: String? = null,
    @SerializedName("noOfDaysForPasswordExpiry") var noOfDaysForPasswordExpiry: Int? = null,
    @SerializedName("maxNoOfAllowedLoginAttempts") var maxNoOfAllowedLoginAttempts: Int? = null,
    @SerializedName("createdBy") var createdBy: String? = null,
    @SerializedName("modifiedBy") var modifiedBy: String? = null,
    @SerializedName("createdDate") var createdDate: String? = null,
    @SerializedName("modifiedDate") var modifiedDate: String? = null,
//    @SerializedName("moduleIds") var moduleIds: Int? = null,
//    @SerializedName("logo") var logo: String? = null,
    @SerializedName("fileName") var fileName: String? = null,
    @SerializedName("fileType") var fileType: String? = null,
    @SerializedName("locationId") var locationId: Int? = null,
    @SerializedName("locationName") var locationName: String? = null,
    @SerializedName("countryName") var countryName: String? = null,
    @SerializedName("stateName") var stateName: String? = null,
    @SerializedName("address1") var address1: String? = null,
    @SerializedName("address2") var address2: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("stateId") var stateId: Int? = null,
    @SerializedName("countryId") var countryId: Int? = null,
    @SerializedName("headOffice") var headOffice: Boolean? = null,
    @SerializedName("employeeId") var employeeId: Int? = null,
    @SerializedName("userId") var userId: Int? = null,
    @SerializedName("username") var username: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("firstName") var firstName: String? = null,
    @SerializedName("lastName") var lastName: String? = null,
    @SerializedName("phoneNumber") var phoneNumber: String? = null,
    @SerializedName("alternateEmail") var alternateEmail: String? = null,
    @SerializedName("alternatePhoneNumber") var alternatePhoneNumber: String? = null,
    @SerializedName("zipCode") var zipCode: String? = null


) : Serializable