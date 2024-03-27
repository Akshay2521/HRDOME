package com.app.hrdrec.admin.roles.get_all_roles_response

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class GetAllRolesResponseData (

  @SerializedName("organizationName" ) var organizationName : String? = null,
  @SerializedName("roleDescription"  ) var roleDescription  : String? = null,
  @SerializedName("organizationId"   ) var organizationId   : Int?    = null,
  @SerializedName("roleId"           ) var roleId           : Int?    = null,
  @SerializedName("roleName"         ) var roleName         : String? = null

):Serializable