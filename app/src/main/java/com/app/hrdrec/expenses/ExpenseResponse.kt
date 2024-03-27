package com.app.hrdrec.expenses

import com.google.gson.annotations.SerializedName


data class ExpenseResponse (
  @SerializedName("status"           ) var status           : Boolean?        = null,
  @SerializedName("statusCode"       ) var statusCode       : Int?            = null,
  @SerializedName("statusMessage"    ) var statusMessage    : String?         = null,
  @SerializedName("errorMessage"     ) var errorMessage     : String?         = null,
  @SerializedName("fromDate"         ) var fromDate         : String?         = null,
  @SerializedName("toDate"           ) var toDate           : String?         = null,
  @SerializedName("purposeOfExpense" ) var purposeOfExpense : String?         = null,
  @SerializedName("data"             ) var data             : ArrayList<ExpenseData> = arrayListOf()
)

class ExpenseData {
  @SerializedName("status"           ) var status           : String? = null
  @SerializedName("remarks"          ) var remarks          : String? = null
  @SerializedName("weekendDate"      ) var weekendDate      : String? = null
  @SerializedName("submittedAmount"      ) var submittedAmount      : Int? = null
  @SerializedName("reimbursedAmount"      ) var reimbursedAmount      : Int? = null
  @SerializedName("approvedAmount"      ) var approvedAmount      : Int? = null
  @SerializedName("submittedDate"      ) var submittedDate      : String? = null
  @SerializedName("reimbursedName"      ) var reimbursedName      : String? = null
  @SerializedName("employeeName"     ) var employeeName     : String? = null
  @SerializedName("organizationId"   ) var organizationId   : Int?    = null
  @SerializedName("locationId"       ) var locationId       : Int?    = null
  @SerializedName("organizationName" ) var organizationName : String? = null
  @SerializedName("locationName"     ) var locationName     : String? = null
  @SerializedName("employeeId"       ) var employeeId       : Int?    = null
  @SerializedName("id"               ) var id               : Int?    = null
  @SerializedName("sheetId"          ) var sheetId     : Int?    = null
  @SerializedName("typeId"          ) var typeId     : Int?    = null
  @SerializedName("typeName"          ) var typeName     : String?    = null
  @SerializedName("categoryName"          ) var categoryName     : String?    = null
  @SerializedName("categoryId"          ) var categoryId     : Int?    = null
  @SerializedName("currencyCode"          ) var currencyCode     : String?    = null
}
