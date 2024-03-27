package com.app.hrdrec.manager.all_leave_submitted

import com.google.gson.annotations.SerializedName

data class LeaveManagerData(
    @SerializedName("reason"           ) var reason           : String? = null,
    @SerializedName("status"           ) var status           : String? = null,
    @SerializedName("managerName"      ) var managerName      : String? = null,
    @SerializedName("organizationId"   ) var organizationId   : Int?    = null,
    @SerializedName("managerId"        ) var managerId        : Int?    = null,
    @SerializedName("leavetypeId"      ) var leavetypeId      : Int?    = null,
    @SerializedName("employeeId"       ) var employeeId       : Int?    = null,
    @SerializedName("fromDate"         ) var fromDate         : String? = null,
    @SerializedName("fromSession"      ) var fromSession      : String? = null,
    @SerializedName("toSession"        ) var toSession        : String? = null,
    @SerializedName("toDate"           ) var toDate           : String? = null,
    @SerializedName("leaveTypeName"    ) var leaveTypeName    : String? = null,
    @SerializedName("employeeName"     ) var employeeName     : String? = null,
    @SerializedName("numberofDays"     ) var numberofDays     : Double? = null,
    @SerializedName("remarks"          ) var remarks          : String? = null,
    @SerializedName("submittedDate"    ) var submittedDate    : String? = null,
    @SerializedName("organizationName" ) var organizationName : String? = null,
    @SerializedName("id"               ) var id               : Int?    = null

)
