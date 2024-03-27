package com.app.hrdrec.leaves.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class LeaveModel (

  @SerializedName("reason"           ) var reason           : String? = null,
  @SerializedName("status"           ) var status           : String? = null,
  @SerializedName("submittedDate"    ) var submittedDate    : String? = null,
  @SerializedName("remarks"          ) var remarks          : String? = null,
  @SerializedName("numberofDays"     ) var numberofDays     : Double? = null,
  @SerializedName("employeeName"     ) var employeeName     : String? = null,
  @SerializedName("leavetypeName"    ) var leavetypeName    : String? = null,
  @SerializedName("organizationId"   ) var organizationId   : Int?    = null,
  @SerializedName("organizationName" ) var organizationName : String? = null,
  @SerializedName("leavetypeId"      ) var leavetypeId      : Int?    = null,
  @SerializedName("employeeId"       ) var employeeId       : Int?    = null,
  @SerializedName("fromDate"         ) var fromDate         : String? = null,
  @SerializedName("toDate"           ) var toDate           : String? = null,
  @SerializedName("fromSession"      ) var fromSession      : String? = null,
  @SerializedName("toSession"        ) var toSession        : String? = null,
  @SerializedName("id"               ) var id               : Int?    = null

):Serializable