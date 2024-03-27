package com.app.hrdrec.projects.clients_response

import com.google.gson.annotations.SerializedName

data class GetClientByOrgResponse (

    @SerializedName("status"        ) var status        : Boolean?        = null,
    @SerializedName("statusCode"    ) var statusCode    : Int?            = null,
    @SerializedName("statusMessage" ) var statusMessage : String?         = null,
    @SerializedName("errorMessage"  ) var errorMessage  : String?         = null,
    @SerializedName("data"          ) var data          : ArrayList<ClientListResponse> = arrayListOf()

)