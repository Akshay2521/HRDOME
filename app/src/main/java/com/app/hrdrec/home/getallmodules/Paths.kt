package com.app.hrdrec.home.getallmodules

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Paths(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("deleted") var deleted: Boolean? = null,
    @SerializedName("path") var path: String? = null,
    var selected: Boolean = false
) : Serializable