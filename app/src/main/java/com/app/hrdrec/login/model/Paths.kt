package com.app.hrdrec.login.model

import com.google.gson.annotations.SerializedName


data class Paths(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("deleted") var deleted: Boolean? = null,
    @SerializedName("path") var path: String? = null

)