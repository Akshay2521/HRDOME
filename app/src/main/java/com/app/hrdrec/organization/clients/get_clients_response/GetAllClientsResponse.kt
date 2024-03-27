package com.app.hrdrec.organization.clients.get_clients_response

import com.google.gson.annotations.SerializedName

class GetAllClientsResponse {

    @SerializedName("status"        ) var status        : Boolean?        = null
    @SerializedName("statusCode"    ) var statusCode    : Int?            = null
    @SerializedName("statusMessage" ) var statusMessage : String?         = null
    @SerializedName("errorMessage"  ) var errorMessage  : String?         = null
    @SerializedName("data"          ) var data          : ArrayList<AllClientsData> = arrayListOf()
}