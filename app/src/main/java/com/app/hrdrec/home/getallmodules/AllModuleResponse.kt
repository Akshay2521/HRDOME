package com.app.hrdrec.home

import com.app.hrdrec.home.getallmodules.ModuleData
import com.app.hrdrec.home.getallmodules.ModuleDataRoles
import com.google.gson.annotations.SerializedName


data class AllModuleResponse(

    @SerializedName("status") var status: Boolean? = null,
    @SerializedName("statusCode") var statusCode: Int? = null,
    @SerializedName("statusMessage") var statusMessage: String? = null,
    @SerializedName("errorMessage") var errorMessage: String? = null,
    @SerializedName("data") var data: ArrayList<ModuleDataRoles> = arrayListOf()

)