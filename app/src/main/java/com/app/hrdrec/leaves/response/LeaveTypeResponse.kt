package com.app.hrdrec.leaves.response

import com.google.gson.annotations.SerializedName

data class LeaveTypeResponse(@SerializedName("status"        ) var status        : Boolean?        = null,
                             @SerializedName("statusCode"    ) var statusCode    : Int?            = null,
                             @SerializedName("statusMessage" ) var statusMessage : String?         = null,
                             @SerializedName("errorMessage"  ) var errorMessage  : String?         = null,
                             @SerializedName("data"          ) var data          : ArrayList<LeaveType> = arrayListOf())