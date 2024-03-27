package com.app.hrdrec.projects

data class AddProjectResourcesPayload (

    val role: String,
    val startDate: String,
    val endDate: String,
    val remarks: String,
    val organizationId: Int,
    val employeeId: Int,
    val projectManagerId: Int

)