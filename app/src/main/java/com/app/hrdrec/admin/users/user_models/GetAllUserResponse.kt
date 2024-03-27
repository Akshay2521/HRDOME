package com.app.hrdrec.admin.users.user_models

import com.google.gson.annotations.SerializedName


data class GetAllUserResponse (

  @SerializedName("status"        ) var status        : Boolean?        = null,
  @SerializedName("statusCode"    ) var statusCode    : Int?            = null,
  @SerializedName("statusMessage" ) var statusMessage : String?         = null,
  @SerializedName("errorMessage"  ) var errorMessage  : String?         = null,
  @SerializedName("data"          ) var data          : ArrayList<UserData> = arrayListOf()

)