package com.app.hrdrec.admin.users.employes_response

import com.google.gson.annotations.SerializedName


data class EmployeeListResponse (

  @SerializedName("organizationName" ) var organizationName : String?  = null,
  @SerializedName("organizationId"   ) var organizationId   : Int?     = null,
  @SerializedName("locationId"       ) var locationId       : Int?     = null,
  @SerializedName("managerId"        ) var managerId        : Int?     = null,
  @SerializedName("email"            ) var email            : String?  = null,
  @SerializedName("employeeId"       ) var employeeId       : Int?     = null,
  @SerializedName("locationName"     ) var locationName     : String?  = null,
  @SerializedName("designation"      ) var designation      : String?  = null,
  @SerializedName("employeeName"     ) var employeeName     : String?  = null,
  @SerializedName("managerName"      ) var managerName      : String?  = null,
  @SerializedName("deleted"          ) var deleted          : Boolean? = null

)