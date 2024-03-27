package com.app.hrdrec.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("code")
    var code: Int,
    @SerializedName("data")
    var `data`: Data,
) {
    data class Data(
        @SerializedName("Email")
        var email: String,
        var password: String
    )
}