package com.app.hrdrec.organization.locations.get_location_response

import com.app.hrdrec.organization.locations.get_location_response.AllLocationData
import com.google.gson.annotations.SerializedName


data class GelAllLocationResponse (

  @SerializedName("status"        ) var status        : Boolean?        = null,
  @SerializedName("statusCode"    ) var statusCode    : Int?            = null,
  @SerializedName("statusMessage" ) var statusMessage : String?         = null,
  @SerializedName("errorMessage"  ) var errorMessage  : String?         = null,
  @SerializedName("data"          ) var data          : ArrayList<AllLocationData> = arrayListOf()

)