package com.app.hrdrec.expenses.dataclasses

data class ExpenseReport(
    val status: Boolean,
    val statusCode: Int,
    val statusMessage: String,
    val errorMessage: String?,
    val data: ExpenseData
)
