package com.app.hrdrec.timesheet.response.project_response

import com.google.gson.annotations.SerializedName


data class ProjectDataResposne (

    @SerializedName("status"        ) var status        : Boolean?        = null,
    @SerializedName("statusCode"    ) var statusCode    : Int?            = null,
    @SerializedName("statusMessage" ) var statusMessage : String?         = null,
    @SerializedName("errorMessage"  ) var errorMessage  : String?         = null,
    @SerializedName("data"          ) var data          : ArrayList<ProjectEmployee> = arrayListOf()

)