package com.app.hrdrec.timesheet.response

import com.google.gson.annotations.SerializedName


data class TimeScheduleData (

  @SerializedName("status"           ) var status           : String? = null,
  @SerializedName("remarks"          ) var remarks          : String? = null,
  @SerializedName("weekendDate"      ) var weekendDate      : String? = null,
  @SerializedName("employeeName"     ) var employeeName     : String? = null,
  @SerializedName("organizationId"   ) var organizationId   : Int?    = null,
  @SerializedName("locationId"       ) var locationId       : Int?    = null,
  @SerializedName("organizationName" ) var organizationName : String? = null,
  @SerializedName("locationName"     ) var locationName     : String? = null,
  @SerializedName("employeeId"       ) var employeeId       : Int?    = null,
  @SerializedName("id"               ) var id               : Int?    = null

)