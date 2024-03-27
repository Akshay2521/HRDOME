package com.app.hrdrec.login.model

import com.google.gson.annotations.SerializedName


data class UserInfo(

  @SerializedName("status"        ) var status        : Boolean? = null,
  @SerializedName("statusCode"    ) var statusCode    : Int?     = null,
  @SerializedName("statusMessage" ) var statusMessage : String?  = null,
  @SerializedName("errorMessage"  ) var errorMessage  : String?  = null,
  @SerializedName("data"          ) var data          : userData?    = userData()

)