package com.app.hrdrec.organization.clients.addclients

data class AddClientsParams(
    val createdBy: Int = 0,
    val createdDate: String? = null,
    val deleted: Boolean? = false,
    val email: String? = null,
    val locationName: String? = null,
    val modifiedBy: Int = 0,
    val modifiedDate: String? = null,
    val name: String? = null,
    val organizationId: Int = 0
)