package com.app.hrdrec.organization.locations.get_location_response

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class AllLocationData(

    @SerializedName("stateName") var stateName: String? = null,
    @SerializedName("organizationName") var organizationName: String? = null,
    @SerializedName("countryId") var countryId: String? = null,
    @SerializedName("stateId") var stateId: String? = null,
    @SerializedName("address1") var address1: String? = null,
    @SerializedName("address2") var address2: String? = null,
    @SerializedName("headOffice") var headOffice: Boolean? = null,
    @SerializedName("locationName") var locationName: String? = null,
    @SerializedName("zipCode") var zipCode: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("organizationId") var organizationId: Int? = null,
    @SerializedName("countryName") var countryName: String? = null,
    @SerializedName("deleted") var deleted: Boolean? = null,
    @SerializedName("id") var id: Int? = null

) : Serializable