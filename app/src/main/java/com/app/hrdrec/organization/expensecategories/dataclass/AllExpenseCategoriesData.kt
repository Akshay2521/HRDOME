package com.app.hrdrec.organization.expensecategories.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AllExpenseCategoriesData(
    @SerializedName("categoryName") var categoryName: String? = null,
    @SerializedName("categoryId") var categoryId: Int? = null,
    @SerializedName("typeId") var typeId: Int? = null,
    @SerializedName("typeName") var typeName: String? = null,
) : Serializable

