package com.app.hrdrec.admin.users

data class AddUserPayload(
    val username: String,
    val roleId: Int,
    val employeeId: Int,
    val organizationId: Int,
)


