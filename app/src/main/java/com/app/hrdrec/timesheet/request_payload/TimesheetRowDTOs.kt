package com.app.hrdrec.timesheet.request_payload

import com.google.gson.annotations.SerializedName

data class TimesheetRowDTOs(
    @SerializedName("sunday"             ) var sunday             : Int?    = null,
    @SerializedName("monday"             ) var monday             : Int?    = null,
    @SerializedName("tuesday"            ) var tuesday            : Int?    = null,
    @SerializedName("wednesday"          ) var wednesday          : Int?    = null,
    @SerializedName("thursday"           ) var thursday           : Int?    = null,
    @SerializedName("friday"             ) var friday             : Int?    = null,
    @SerializedName("saturday"           ) var saturday           : Int?    = null,
    @SerializedName("status"             ) var status             : String? = null,
    @SerializedName("remarks"            ) var remarks            : String? = null,
    @SerializedName("createdBy"          ) var createdBy          : Int?    = null,
    @SerializedName("modifiedBy"         ) var modifiedBy         : Int?    = null,
    @SerializedName("task"               ) var task               : String? = null,
    @SerializedName("projectId"          ) var projectId          : Int?    = null,
    @SerializedName("projectName"        ) var projectName        : String? = null,
    @SerializedName("projectManagerName" ) var projectManagerName : String? = null,

)
