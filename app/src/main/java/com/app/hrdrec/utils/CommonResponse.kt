package com.app.hrdrec.utils

import com.google.gson.annotations.SerializedName


data class CommonResponse (

  @SerializedName("status"        ) var status        : Boolean? = null,
  @SerializedName("statusCode"    ) var statusCode    : Int?     = null,
  @SerializedName("statusMessage" ) var statusMessage : String?  = null,
  @SerializedName("message" ) var message : String?  = null,
  @SerializedName("errorMessage"  ) var errorMessage  : String?  = null,
)