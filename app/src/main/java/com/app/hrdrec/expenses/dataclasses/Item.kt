package com.app.hrdrec.expenses.dataclasses

import com.google.gson.annotations.SerializedName

data class Item(
    val id: Int,
    val typeId: Int,
    @SerializedName("typeName") val typeName: String,
    @SerializedName("categoryId") val categoryId: Int,
    @SerializedName("categoryName") val categoryName: String,
    var checked: Boolean,
    var currencyId: Int,
    var receipts: Array<String>,
    var date: String,
    var submittedAmount: Int,
    var remarks: String,
    var status: String,
    var approvedAmount: Int?,
    var approvedRemarks: String?,
    var reimbursedAmount: Int?,
    var reimbursedRemarks: String?,
    @SerializedName("currencyCode") val currencyCode: String
)
