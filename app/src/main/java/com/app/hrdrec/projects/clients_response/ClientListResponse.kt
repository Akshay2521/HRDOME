package com.app.hrdrec.projects.clients_response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ClientListResponse (

    @SerializedName("locationName") var locationName: String? = null,
    @SerializedName("organizationName") var organizationName: String? = null,
    @SerializedName("organizationId") var organizationId: Int? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("deleted") var deleted: Boolean? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("id") var id: Int? = null

) : Serializable