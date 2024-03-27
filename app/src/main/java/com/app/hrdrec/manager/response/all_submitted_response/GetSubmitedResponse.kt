package com.app.hrdrec.manager.response.all_submitted_response

import com.google.gson.annotations.SerializedName

data class GetSubmitedResponse(

    @SerializedName("status"        ) var status        : Boolean?        = null,
    @SerializedName("statusCode"    ) var statusCode    : Int?            = null,
    @SerializedName("statusMessage" ) var statusMessage : String?         = null,
    @SerializedName("errorMessage"  ) var errorMessage  : String?         = null,
    @SerializedName("fromDate"      ) var fromDate      : String?         = null,
    @SerializedName("toDate"        ) var toDate        : String?         = null,
    @SerializedName("data"          ) var data          : ArrayList<GetSubmitedManagerModel> = arrayListOf()

)
