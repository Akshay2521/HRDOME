package com.app.hrdrec.organization.locations.state_response

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("id"            ) var id            : Int?                 = null,
  @SerializedName("name"          ) var name          : String?              = null,
  @SerializedName("isdCode"       ) var isdCode       : String?              = null,
  @SerializedName("telephoneCode" ) var telephoneCode : String?              = null,
  @SerializedName("stateDTOs"     ) var stateDTOs     : ArrayList<StateDTOs> = arrayListOf()

)