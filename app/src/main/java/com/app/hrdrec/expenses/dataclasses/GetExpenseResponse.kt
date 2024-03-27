package com.app.hrdrec.expenses.dataclasses

import com.app.hrdrec.expenses.ExpenseResponse
import com.google.gson.annotations.SerializedName

data class GetExpenseResponse (

    @SerializedName("status"        ) var status        : Boolean? = null,
    @SerializedName("statusCode"    ) var statusCode    : Int?     = null,
    @SerializedName("statusMessage" ) var statusMessage : String?  = null,
    @SerializedName("errorMessage"  ) var errorMessage  : String?  = null,
    @SerializedName("data"  ) var data  : ExpenseResponse?  = null,
)
