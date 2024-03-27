package com.app.hrdrec.projects

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProjectData (

    @SerializedName("description") var description: String? = null,
    @SerializedName("projectManagerId") var projectManagerId: Int? = null,
    @SerializedName("projectId") var projectId: Int? = null,
    @SerializedName("organizationName") var organizationName: String? = null,
    @SerializedName("projectManagerName") var projectManagerName: String? = null,
    @SerializedName("expectedStartDate") var expectedStartDate: String? = null,
    @SerializedName("projectClientId") var projectClientId: Int? = null,
    @SerializedName("organizationId") var organizationId: Int? = null,
    @SerializedName("technology") var technology: String? = null,
    @SerializedName("deleted") var deleted: Boolean? = null,
    @SerializedName("billable") var billable: String? = null,
    @SerializedName("projectName") var projectName: String? = null,
    @SerializedName("projectStatusName") var projectStatusName: String? = null,
    @SerializedName("projectClientName") var projectClientName: String? = null,
    @SerializedName("role") var role: String? = null,
    @SerializedName("employeeId") var employeeId: Int? = null,
    @SerializedName("employeeName") var employeeName: String? = null,
    @SerializedName("startDate") var startDate: String? = null,
    @SerializedName("endDate") var endDate: String? = null,
    @SerializedName("remarks") var remarks: String? = null

) : Serializable