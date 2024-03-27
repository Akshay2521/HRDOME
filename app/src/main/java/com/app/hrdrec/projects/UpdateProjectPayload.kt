package com.app.hrdrec.projects

import android.widget.EditText

data class UpdateProjectPayload (

    val projectStatusId: String,
    val name: String,
    val description: String,
    val technology: String,
    val expectedStartDate: String,
    val expectedEndDate: String,
    val actualStartDate: String,
    val actualEndDate: String,
    val organizationId: Int,
    val projectClientId: Int,
    val id: Int

    )