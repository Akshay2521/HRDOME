package com.app.hrdrec.admin.users.user_models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class UserData(

    @SerializedName("username") var username: String? = null,
    @SerializedName("organizationName") var organizationName: String? = null,
    @SerializedName("organizationId") var organizationId: Int? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("employeeId") var employeeId: Int? = null,
    @SerializedName("locationName") var locationName: String? = null,
    @SerializedName("phoneNumber") var phoneNumber: String? = null,
    @SerializedName("roleId") var roleId: Int? = null,
    @SerializedName("counter") var counter: Int? = null,
    @SerializedName("employeeName") var employeeName: String? = null,
    @SerializedName("roleName") var roleName: String? = null,
    @SerializedName("deleted") var deleted: Boolean? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("locked") var locked: Boolean? = null

) : Serializable