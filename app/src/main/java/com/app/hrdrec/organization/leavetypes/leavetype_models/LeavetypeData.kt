package com.app.hrdrec.organization.leavetypes.leavetype_models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LeavetypeData (

    @SerializedName("organizationId") var organizationId: Int? = null,
    @SerializedName("locationId") var locationId: Int? = null,
    @SerializedName("locationName") var locationName: String? = null,
    @SerializedName("specialTypeEnum") var specialTypeEnum: String? = null,
    @SerializedName("genderEnum") var genderEnum: String? = null,
    @SerializedName("maxcarryforward") var maxcarryforward: Float? = null,
    @SerializedName("numberOfDays") var numberOfDays: Float? = null,
    @SerializedName("frequency") var frequency: String? = null,
    @SerializedName("automate") var automate: String? = null,
    @SerializedName("carryforward") var carryforward: String? = null,
    @SerializedName("deleted") var deleted: Boolean? = null,
    @SerializedName("organizationName") var organizationName: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("id") var id: Int? = null

) : Serializable