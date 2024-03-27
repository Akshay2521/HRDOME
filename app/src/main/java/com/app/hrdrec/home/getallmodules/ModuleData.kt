package com.app.hrdrec.home.getallmodules

import com.app.hrdrec.home.getallmodules.Paths
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ModuleData(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("pathDTOs") var paths: ArrayList<Paths> = arrayListOf()

) : Serializable