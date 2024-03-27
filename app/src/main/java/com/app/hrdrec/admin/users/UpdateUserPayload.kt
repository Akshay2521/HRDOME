package com.app.hrdrec.admin.users

data class UpdateUserPayload(
val username: String,
val roleId: Int,
val employeeId: Int,
val organizationId: Int,
val id: Int
)