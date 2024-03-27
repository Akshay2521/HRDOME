package com.app.hrdrec.manager.all_leave_submitted

import com.google.gson.annotations.SerializedName

data class GetLeaveManagerModel(
    @SerializedName("status"        ) var status        : Boolean?        = null,
    @SerializedName("statusCode"    ) var statusCode    : Int?            = null,
    @SerializedName("statusMessage" ) var statusMessage : String?         = null,
    @SerializedName("errorMessage"  ) var errorMessage  : String?         = null,
    @SerializedName("fromDate"      ) var fromDate      : String?         = null,
    @SerializedName("toDate"        ) var toDate        : String?         = null,
    @SerializedName("data"          ) var data          : ArrayList<LeaveManagerData> = arrayListOf()

)
