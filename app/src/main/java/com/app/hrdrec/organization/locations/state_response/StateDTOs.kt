package com.app.hrdrec.organization.locations.state_response

import com.google.gson.annotations.SerializedName


data class StateDTOs (

  @SerializedName("id"   ) var id   : Int?    = null,
  @SerializedName("name" ) var name : String? = null

)