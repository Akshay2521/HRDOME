package com.app.hrdrec.manager.response.all_submitted_response

import com.google.gson.annotations.SerializedName

data class GetSubmitedManagerModel(
    @SerializedName("status"             ) var status             : String? = null,
    @SerializedName("employeeId"         ) var employeeId         : Int?    = null,
    @SerializedName("weekendDate"        ) var weekendDate        : String? = null,
    @SerializedName("projectId"          ) var projectId          : Int?    = null,
    @SerializedName("employeeName"       ) var employeeName       : String? = null,
    @SerializedName("projectManagerId"   ) var projectManagerId   : Int?    = null,
    @SerializedName("projectManagerName" ) var projectManagerName : String? = null,
    @SerializedName("projectName"        ) var projectName        : String? = null,
    @SerializedName("id"                 ) var id                 : Int?    = null

)
