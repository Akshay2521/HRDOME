package com.app.hrdrec.expenses

import com.google.gson.annotations.SerializedName

data class AddExpenseModel(
    //@SerializedName("weekendDate") var weekendDate: String? = null,
    //@SerializedName("totalHours") var totalHours: Double? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("employeeId") var employeeId: Int? = null,
    @SerializedName("organizationId") var organizationId: Int? = null,
    @SerializedName("locationId") var locationId: Int? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("fromDate") var fromDate: String? = null,
    @SerializedName("toDate") var toDate: String? = null,
    @SerializedName("purposeOfExpense") var purposeOfExpense: String? = null,
    @SerializedName("submittedDate") var submittedDate: String? = null,
    @SerializedName("approvedDate") var approvedDate: String? = null,
    @SerializedName("reimbursedDate") var reimbursedDate: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("items") var items: List<Item>? = null
) {
    data class Item(
        @SerializedName("status") var status: String? = null,
        @SerializedName("typeId") var typeId: Int? = null,
        @SerializedName("typeName") var typeName: String? = null,
        @SerializedName("categoryId") var categoryId: Int? = null,
        @SerializedName("categoryName") var categoryName: String? = null,
        @SerializedName("checked") var checked: Boolean? = null,
        @SerializedName("currencyId") var currencyId: Int? = null,
        @SerializedName("receipts") var receipts: List<Receipt>? = null,
        @SerializedName("date") var date: String? = null,
        @SerializedName("submittedAmount") var submittedAmount: String? = null,
        @SerializedName("remarks") var remarks: String? = null,
        @SerializedName("currencyCode") var currencyCode: String? = null
    ) {
        data class Receipt(
            @SerializedName("fileName") var fileName: String? = null,
            @SerializedName("fileType") var fileType: String? = null
        )
    }
}
