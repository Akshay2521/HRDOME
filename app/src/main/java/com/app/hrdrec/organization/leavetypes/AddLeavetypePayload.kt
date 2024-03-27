package com.app.hrdrec.organization.leavetypes

import android.widget.EditText

data class AddLeavetypePayload(

    val specialTypeEnum: String,
    val name: String,
    val frequency: String,
    val automate: String,
    val genderEnum: String,
    val numberOfDays: EditText,
    val carryforward: String,
    val maxcarryforward: EditText,
    val organizationId: Int,
    val locationId: Int,

    )