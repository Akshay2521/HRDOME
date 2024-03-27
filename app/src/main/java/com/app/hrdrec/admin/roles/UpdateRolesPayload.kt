package com.app.hrdrec.admin.roles


data class UpdateRolesPayload(
    val name: String,
    val description: String,
    val pathIds: List<Int>,
    val locationIds: List<Int>,
    val organizationId: Int,
    val id: Int,
)