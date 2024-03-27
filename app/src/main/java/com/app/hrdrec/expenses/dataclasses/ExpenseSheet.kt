package com.app.hrdrec.expenses.dataclasses

import com.app.hrdrec.expenses.AddExpenseModel
import com.google.gson.annotations.SerializedName

data class ExpenseSheet(
    @SerializedName("expenseSheet") val addExpenseModel: AddExpenseModel
)
