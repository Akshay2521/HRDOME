package com.app.hrdrec.admin.users.employes_response

import com.google.gson.annotations.SerializedName


data class GetEmployeeByOrgResponse (

  @SerializedName("status"        ) var status        : Boolean?        = null,
  @SerializedName("statusCode"    ) var statusCode    : Int?            = null,
  @SerializedName("statusMessage" ) var statusMessage : String?         = null,
  @SerializedName("errorMessage"  ) var errorMessage  : String?         = null,
  @SerializedName("data"          ) var data          : ArrayList<EmployeeListResponse> = arrayListOf()

)