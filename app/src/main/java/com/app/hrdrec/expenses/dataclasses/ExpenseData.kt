package com.app.hrdrec.expenses.dataclasses

import com.google.gson.annotations.SerializedName

data class ExpenseData(
    var id: Int,
    @SerializedName("fromDate") var fromDate: String,
    @SerializedName("toDate") var toDate: String,
    @SerializedName("purposeOfExpense") var purposeOfExpense: String,
    @SerializedName("submittedDate") var submittedDate: String,
    @SerializedName("approvedDate") var approvedDate: String?,
    @SerializedName("reimbursedDate") var reimbursedDate: String?,
    var description: String?,
    var status: String,
    @SerializedName("employeeId") var employeeId: Int,
    @SerializedName("employeeName") var employeeName: String,
    @SerializedName("locationName") var locationName: String,
    @SerializedName("managerId") var managerId: Int,
    @SerializedName("managerName") var managerName: String,
    var reimbursedBy: String?,
    var reimbursedName: String?,
    var referenceNo: String?,
    var items: Array<Item>
)
