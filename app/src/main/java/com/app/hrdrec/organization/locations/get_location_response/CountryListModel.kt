package com.app.hrdrec.organization.locations.get_location_response

import com.google.gson.annotations.SerializedName

data class CountryListModel(
    @SerializedName("isdNumberCode" ) var isdNumberCode : String? = null,
    @SerializedName("isoCode"       ) var isoCode       : String? = null,
    @SerializedName("isdCode"       ) var isdCode       : Int?    = null,
    @SerializedName("name"          ) var name          : String? = null,
    @SerializedName("id"            ) var id            : Int?    = null

)
