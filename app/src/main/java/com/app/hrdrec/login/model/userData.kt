package com.app.hrdrec.login.model

import com.app.hrdrec.home.getallmodules.ModuleData
import com.google.gson.annotations.SerializedName


data class userData(

  @SerializedName("username"         ) var username         : String?            = null,
  @SerializedName("code"             ) var code             : String?            = null,
  @SerializedName("email"            ) var email            : String?            = null,
  @SerializedName("firstName"        ) var firstName        : String?            = null,
  @SerializedName("lastName"         ) var lastName         : String?            = null,
  @SerializedName("organizationId"   ) var organizationId   : Int?               = null,
  @SerializedName("organizationName" ) var organizationName : String?            = null,
  @SerializedName("employeeId"       ) var employeeId       : Int?               = null,
  @SerializedName("modules"          ) var modules          : ArrayList<ModuleData> = arrayListOf(),
  @SerializedName("paths"            ) var paths            : String?            = null,
  @SerializedName("locationId"       ) var locationId       : Int?               = null,
  @SerializedName("locationName"     ) var locationName     : String?            = null,
  @SerializedName("preNotification"  ) var preNotification  : Boolean?           = null,
  @SerializedName("contentCreator"   ) var contentCreator   : Boolean?           = null,
  @SerializedName("token"            ) var token            : String?            = null

  /* @SerializedName("username"       ) var username       : String?          = null,
   @SerializedName("code"           ) var code           : String?          = null,
   @SerializedName("email"          ) var email          : String?          = null,
   @SerializedName("firstName"      ) var firstName      : String?          = null,
   @SerializedName("lastName"       ) var lastName       : String?          = null,
   @SerializedName("organizationId" ) var organizationId : Int?             = null,
   @SerializedName("employeeId"     ) var employeeId     : Int?             = null,
   @SerializedName("modules"        ) var modules        : ArrayList<Int>   = arrayListOf(),
   @SerializedName("paths"          ) var paths          : ArrayList<Paths> = arrayListOf(),
   @SerializedName("locationId"     ) var locationId     : Int?             = null*/
)

/*
data class Modules (

  @SerializedName("id"          ) var id          : Int?                = null,
  @SerializedName("name"        ) var name        : String?             = null,
  @SerializedName("description" ) var description : String?             = null,
  @SerializedName("pathDTOs"    ) var pathDTOs    : ArrayList<PathDTOs> = arrayListOf()

)

data class PathDTOs (

  @SerializedName("id"      ) var id      : Int?     = null,
  @SerializedName("name"    ) var name    : String?  = null,
  @SerializedName("deleted" ) var deleted : Boolean? = null,
  @SerializedName("path"    ) var path    : String?  = null

)*/
