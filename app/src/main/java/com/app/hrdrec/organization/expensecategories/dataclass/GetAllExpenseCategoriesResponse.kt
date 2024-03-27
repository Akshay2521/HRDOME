package com.app.hrdrec.organization.expensecategories.dataclass

import com.app.hrdrec.organization.HolidayCalendars.dataclass.AllHolidayCalendarData
import com.google.gson.annotations.SerializedName

data class GetAllExpenseCategoriesResponse(
    @SerializedName("status"        ) var status        : Boolean?        = null,
    @SerializedName("statusCode"    ) var statusCode    : Int?            = null,
    @SerializedName("statusMessage" ) var statusMessage : String?         = null,
    @SerializedName("errorMessage"  ) var errorMessage  : String?         = null,
    @SerializedName("data"          ) var data          : ArrayList<AllExpenseCategoriesData> = arrayListOf()
)