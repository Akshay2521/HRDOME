package com.app.hrdrec.organization.organizationprofile.get_organizationprofile_response

import com.google.gson.annotations.SerializedName

class GetAllOrganizationProfileResponse {

    @SerializedName("status"        ) var status        : Boolean?        = null
    @SerializedName("statusCode"    ) var statusCode    : Int?            = null
    @SerializedName("statusMessage" ) var statusMessage : String?         = null
    @SerializedName("errorMessage"  ) var errorMessage  : String?         = null
    @SerializedName("data"          ) var data          : AllOrganizationProfileData? = null

}