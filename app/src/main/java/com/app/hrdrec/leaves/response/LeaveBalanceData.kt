package com.app.hrdrec.leaves.response

import com.google.gson.annotations.SerializedName

data class LeaveBalanceData(
    @SerializedName("leaveTypeName" ) var leaveTypeName : String? = null,
    @SerializedName("totalCredited" ) var totalCredited : Int?    = null,
    @SerializedName("totalUsed"     ) var totalUsed     : Double? = null,
    @SerializedName("remaining"     ) var remaining     : Double? = null,
    @SerializedName("employeeId"    ) var employeeId    : Int?    = null,
    @SerializedName("leaveTypeId"   ) var leaveTypeId   : Int?    = null
)
