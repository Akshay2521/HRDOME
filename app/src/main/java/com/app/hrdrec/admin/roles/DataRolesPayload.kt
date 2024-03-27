package com.app.hrdrec.admin.roles

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class DataRolesPayload(

    val name: String,
    val description: String,
    val pathIds: List<Int>,
    val locationIds: List<Int>,
    val organizationId: Int,
)