package com.app.hrdrec.timesheet.request_payload

import com.google.gson.annotations.SerializedName

data class SavedDataResponse(@SerializedName("status"        ) var status        : Boolean? = null,
                             @SerializedName("statusCode"    ) var statusCode    : Int?     = null,
                             @SerializedName("statusMessage" ) var statusMessage : String?  = null,
                             @SerializedName("errorMessage"  ) var errorMessage  : String?  = null,
                             @SerializedName("data"          ) var data          : AddTimeModal?    = AddTimeModal()
)
