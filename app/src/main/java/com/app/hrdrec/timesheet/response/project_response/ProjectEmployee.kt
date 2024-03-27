package com.app.hrdrec.timesheet.response.project_response

import com.google.gson.annotations.SerializedName


data class ProjectEmployee (
    @SerializedName("projectStatusId"    ) var projectStatusId    : Int?    = null,
    @SerializedName("projectId"          ) var projectId          : Int?    = null,
    @SerializedName("projectManagerId"   ) var projectManagerId   : Int?    = null,
    @SerializedName("projectManagerName" ) var projectManagerName : String? = null,
    @SerializedName("projectName"        ) var projectName        : String? = null,
    @SerializedName("projectStatusName"  ) var projectStatusName  : String? = null

)