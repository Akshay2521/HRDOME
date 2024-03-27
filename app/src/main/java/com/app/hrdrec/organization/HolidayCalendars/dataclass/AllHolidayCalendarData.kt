package com.app.hrdrec.organization.HolidayCalendars.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AllHolidayCalendarData(
    @SerializedName("locationName") var locationName: String? = null,
    @SerializedName("locationId") var locationId: Int? = null,
    @SerializedName("year") var year: Int? = null
) : Serializable
