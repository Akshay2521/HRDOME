package com.app.hrdrec.leaves.response

import com.google.gson.annotations.SerializedName

/*data class LeaveType(@SerializedName("frequency"        ) var frequency        : String?  = null,
                     @SerializedName("organizationId"   ) var organizationId   : Int?     = null,
                     @SerializedName("locationId"       ) var locationId       : Int?     = null,
                     @SerializedName("organizationName" ) var organizationName : String?  = null,
                     @SerializedName("locationName"     ) var locationName     : String?  = null,
                     @SerializedName("deleted"          ) var deleted          : Boolean? = null,
                     @SerializedName("maxlimit"         ) var maxlimit         : Int?     = null,
                     @SerializedName("numberOfDays"     ) var numberOfDays     : Int?     = null,
                     @SerializedName("genderEnum"       ) var genderEnum       : String?  = null,
                     @SerializedName("maxcarryforward"  ) var maxcarryforward  : Int?     = null,
                     @SerializedName("specialTypeEnum"  ) var specialTypeEnum  : String?  = null,
                     @SerializedName("carryforward"     ) var carryforward     : Boolean? = null,
                     @SerializedName("specailType"      ) var specailType      : Boolean? = null,
                     @SerializedName("automate"         ) var automate         : Boolean? = null,
                     @SerializedName("genderSpecific"   ) var genderSpecific   : Boolean? = null,
                     @SerializedName("name"             ) var name             : String?  = null,
                     @SerializedName("id"               ) var id               : Int?     = null)*/

data class LeaveType (

    @SerializedName("id"               ) var id               : Int?              = null,
    @SerializedName("name"             ) var name             : String?           = null,
    @SerializedName("numberOfDays"     ) var numberOfDays     : Double?           = null,
    @SerializedName("maxlimit"         ) var maxlimit         : Int?              = null,
    @SerializedName("deleted"          ) var deleted          : Boolean?          = null,
    @SerializedName("carryforward"     ) var carryforward     : Boolean?          = null,
    @SerializedName("maxcarryforward"  ) var maxcarryforward  : Int?              = null,
    @SerializedName("automate"         ) var automate         : Boolean?          = null,
    @SerializedName("specialTypeEnum"  ) var specialTypeEnum  : String?           = null,
    @SerializedName("frequency"        ) var frequency        : String?           = null,
    @SerializedName("organizationId"   ) var organizationId   : Int?              = null,
    @SerializedName("organizationName" ) var organizationName : String?           = null,
    @SerializedName("locationId"       ) var locationId       : Int?              = null,
    @SerializedName("locationName"     ) var locationName     : String?           = null,
    @SerializedName("genderEnums"      ) var genderEnums      : ArrayList<String> = arrayListOf()

)
